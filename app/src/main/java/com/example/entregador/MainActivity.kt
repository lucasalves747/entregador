package  com.example.entregador
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.entregador.historico.Historico
import com.example.entregador.model.Pedido
import com.example.entregador.recyclerView.ListapedidosAdapter
import com.example.entregador.requests.PushLocalizacao
import com.example.entregador.requests.ReqPedidodsPendents
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity() : AppCompatActivity(), Runnable {

    private lateinit var recyclerView: RecyclerView
    private val handler = Handler()
    private lateinit var pedidos: List<Pedido>
    private var executaAtualizacaoDelocalizacaoEpedidos = true
    private var atualizarpedido = true
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var token: String? = null
    private var permissao:String? = null
    private var ultimaLatitude: Double = 0.0
    private var ultimaLongitude: Double = 0.0
    private var aceitouLocalizacao = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = buscarTokenUsuario()
        if (token.equals(null)) {
            val segundaTela = Intent(this,Login::class.java)
            ContextCompat.startActivity(this, segundaTela, null)
        }

        permissao  = buscarPermissao()


        setContentView(R.layout.activity_main)

        val botaoHistorico = findViewById<ImageButton>(R.id.botaoHiastorico)
        botaoHistorico.setOnClickListener {
            val segundaTela = Intent(this, Historico::class.java)
            segundaTela.putExtra("token", token)
            ContextCompat.startActivity(this, segundaTela, null)
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            interval = 2000 // Intervalo de atualização em milissegundos
            fastestInterval = 1000 // Intervalo mais rápido em milissegundos
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        recyclerView = findViewById(R.id.recyclerView)

        val requesPedidos: List<Pedido> = pedidosFromJson(ReqPedidodsPendents(token).execute().get())
        pedidos = requesPedidos
        creatRecycleview(requesPedidos, token)

        Toast.makeText(this, "${permissao}", Toast.LENGTH_SHORT).show()
        if(permissao.equals(null)){
            exibirSettingDelocalizacao(this)

            if(aceitouLocalizacao) {
                val sharedPreferences =
                    this.getSharedPreferences("permissao", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("permissao", "ja pediu")
                editor.apply()
            }

        }

        run()
    }

    private fun creatRecycleview(pedidos: List<Pedido>, token: String?) {
        recyclerView.clearAnimation()
        recyclerView.adapter = ListapedidosAdapter(
            context = this, pedidos, token
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun enviaLocalizacao() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        if (location.latitude != ultimaLatitude && location.longitude != ultimaLongitude) {
                            GlobalScope.launch(Dispatchers.IO) {
                                try {
                                    PushLocalizacao(location.latitude, location.longitude, token).execute()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    // Trate exceções, se necessário
                                }
                            }
                            ultimaLatitude = location.latitude
                            ultimaLongitude = location.longitude
                        }
                    } else {
                        exibirDialogAtivarLocalizacao(this@MainActivity)
                    }
                }
            },
            null
        )
    }

    var i = 0
    override fun run() {
        if (atualizarpedido && i >= 10) {
            val requesPedidos: List<Pedido> = pedidosFromJson(ReqPedidodsPendents(token).execute().get())

            if(requesPedidos.size != pedidos.size) {
                Toast.makeText(this,"Desenhando",Toast.LENGTH_SHORT).show()
                creatRecycleview(requesPedidos, token)
                pedidos = requesPedidos
            }else{
                Toast.makeText(this,"ferente",Toast.LENGTH_SHORT).show()

            }
        }

        enviaLocalizacao()
        if (executaAtualizacaoDelocalizacaoEpedidos) {
            handler.postDelayed(this, 3000)
        }
        i++
        if(i == 11){
            i=0
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

    override fun onResume() {
        atualizarpedido = true
        super.onResume()
    }

    override fun onPause() {
        atualizarpedido = false
        super.onPause()
    }

    private fun buscarTokenUsuario(): String? {
        val sharedPreferences = this.getSharedPreferences("chave-token", MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

    private fun buscarPermissao():String?{
        val sharedPreferences = this.getSharedPreferences("permissao", MODE_PRIVATE)
        return sharedPreferences.getString("permissao", null)
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

    public fun exibirSettingDelocalizacao(activity: FragmentActivity){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Permissão de Localização")
            .setMessage("Para fornecer a melhor experiência, precisamos acessar sua localização o tempo todo. Por favor, conceda a permissão de localização nas configurações do aplicativo.")
            .setPositiveButton("Permitir Localização") { _, _ ->
                aceitouLocalizacao = true
                // Abra diretamente as configurações de permissão de localização
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)

            }
            .setNegativeButton("Cancelar") { _, _ ->
                // Lidar com o cancelamento

                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Permissão de Localização")
                    .setMessage("Infelizmente não sera possivel usar o app")
                    .setPositiveButton("ok") { _, _ ->
                        finishAffinity()
                    }
                    .show()
            }
            .show()

    }
}
