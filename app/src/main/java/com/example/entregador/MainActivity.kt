package com.example.entregador

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.entregador.historico.Historico
import com.example.entregador.model.Pedido
import com.example.entregador.recyclerView.ListapedidosAdapter
import com.example.entregador.requests.ReqPedidodsPendents
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), Runnable {

    lateinit var recyclerView: RecyclerView
    var handler = Handler()
    lateinit var pedidos: List<Pedido>
    var executaAtualizacaoDelocalizacaoEpedidos = true
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var token:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = buscarTokenUsuario()
        if (token.equals(null)) {
            val segundaTela = Intent(this, Login::class.java)
            ContextCompat.startActivity(this, segundaTela, null)
        }

        setContentView(R.layout.activity_main)

        val botaoHistorico = findViewById<ImageButton>(R.id.botaoHiastorico)
        botaoHistorico.setOnClickListener {
            val segundaTela = Intent(this, Historico::class.java)
            ContextCompat.startActivity(this, segundaTela, null)
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val requesPedidos: List<Pedido> =
            pedidosFromJson(ReqPedidodsPendents(token).execute().get())

        creatRecycleview(listOf(Pedido(1, BigDecimal("40"),"lucas","ola")))

        run()
    }


    fun creatRecycleview(pedidos: List<Pedido>) {
        recyclerView.clearAnimation()
        recyclerView.adapter = ListapedidosAdapter(
            context = this, pedidos
        )
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun enviaLocalizacao() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )

            return
        }
        task.addOnSuccessListener {

            if (it != null) {
                //Requests().pushLocalizacao(Localizacao(it.latitude,it.longitude))
                Toast.makeText(this, "${it.longitude} ${it.latitude}", Toast.LENGTH_LONG).show()
            } else {
                exibirDialogAtivarLocalizacao(this)
            }

        }
    }
    var i = 0
    override fun run() {
        val requesPedidos: List<Pedido> =
            pedidosFromJson(ReqPedidodsPendents(token).execute().get())

        if (i == 5) {
            ListapedidosAdapter(this, listOf(Pedido(1, BigDecimal("3"),"yasmin","casa"))).atualizarLista()

        }
        i++

        enviaLocalizacao()
        if (executaAtualizacaoDelocalizacaoEpedidos) {
            handler.postDelayed(this, 5000)
        }
    }

    private fun pedidosFromJson(json: String?): List<Pedido> {
        val gson = Gson()
        val listaTipo = object : TypeToken<List<Pedido>>() {}.type
        return gson.fromJson(json, listaTipo)

    }

    fun setFalseExecutaAtualizacaoDelocalizacaoEpedidos() {
        executaAtualizacaoDelocalizacaoEpedidos = false
    }

    override fun onDestroy() {
        executaAtualizacaoDelocalizacaoEpedidos = false
        run()
        super.onDestroy()
    }

    fun buscarTokenUsuario(): String? {
        val sharedPreferences =
            this.getSharedPreferences("chave-token", MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

    private fun exibirDialogAtivarLocalizacao(activity: FragmentActivity) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Ativar Localização")
            .setMessage("A localização está desativada. Por favor, ative-a nas configurações.")
            .setPositiveButton("Configurações") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
            .setNegativeButton("Cancelar") { _, _ ->
                // Lidar com o cancelamento
            }
            .show()
    }

}