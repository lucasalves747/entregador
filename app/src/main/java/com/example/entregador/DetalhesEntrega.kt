package com.example.entregador

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.CalendarContract.Colors
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.entregador.model.PedidoDetalhado
import com.example.entregador.requests.AceitarPedido
import com.example.entregador.requests.ReqPedidoDetalhes
import com.example.entregador.services.CriadorDeUriLocal
import com.example.entregador.urls.Url
import com.google.gson.Gson

class DetalhesEntrega : AppCompatActivity() {

    var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_entrega2)

        val id = intent.getStringExtra("id")
        val token = intent.getStringExtra("token")

        val nome_LocalOrigem = findViewById<TextView>(R.id.nomeLocal_origem)
        val localorigem = findViewById<TextView>(R.id.Local_Origem)
        val nomeLocalDestino = findViewById<TextView>(R.id.nomeLocal_destino)
        val localDestino = findViewById<TextView>(R.id.Local_destino)
        val botaoRota = findViewById<Button>(R.id.rota)
        val botaoAceita_Entregue = findViewById<Button>(R.id.aceitar_entregue)
        val valor = findViewById<TextView>(R.id.valorText)
        val observacao = findViewById<TextView>(R.id.observacaoText)
        val itensPedido = findViewById<TextView>(R.id.itensPedidoText)

        val req = ReqPedidoDetalhes(token,id).execute().get()
        val pedido = TransFormePedido(req)

        nome_LocalOrigem.text = "origem" //pedido.nomeEstabelecimento
        localorigem.text = pedido.localOrigem
        nomeLocalDestino.text = "destino"
        localDestino.text = pedido.localDestino
        valor.text = pedido.valor.toPlainString()
        observacao.text = pedido.observacao
        itensPedido.text = pedido.itensDoPedido




        if(pedido.status == "INICIAR"){
            botaoAceita_Entregue.text = "ACEITAR"
        }
        else{
            botaoAceita_Entregue.text = "ENTREGAR"
        }

        botaoRota.setOnClickListener(View.OnClickListener {
            val uri = Uri.parse(CriadorDeUriLocal().creatUri(pedido))
            val intent = Intent(Intent.ACTION_VIEW,uri)
            intent.setPackage("com.google.android.apps.maps")
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        })

        botaoAceita_Entregue.setOnClickListener {

            AceitarPedido(token,"${Url().urlRoot}/pedidos/mudarEstadopedido/${id}").execute().get()



            if(pedido.status == "INICIAR"){
                val uri = Uri.parse(CriadorDeUriLocal().creatUri(pedido))
                val intent = Intent(Intent.ACTION_VIEW,uri)
                intent.setPackage("com.google.android.apps.maps")
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }else{

                finish()
            }

            botaoAceita_Entregue.text = "ENTREGAR"
           // botaoAceita_Entregue.setBackgroundColor(ContextCompat.getColor(this,R.color.verdeBotao))
            pedido.status = "em rota"
        }
        }



    private fun TransFormePedido(req: String?): PedidoDetalhado {
        var gson = Gson()
        return gson.fromJson(req,PedidoDetalhado::class.java)
    }



}