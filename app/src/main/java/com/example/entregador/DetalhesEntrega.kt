package com.example.entregador

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.entregador.services.CriadorDeUriLocal

class DetalhesEntrega : AppCompatActivity(),Runnable {

    var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_entrega2)

        val nome_LocalOrigem = findViewById<TextView>(R.id.nomeLocal_origem)
        val localorigem = findViewById<TextView>(R.id.Local_Origem)
        val nomeLocalDestino = findViewById<TextView>(R.id.nomeLocal_destino)
        val localDestino = findViewById<TextView>(R.id.Local_destino)
        val botaoRota = findViewById<Button>(R.id.rota)

        val req = Requests()
        val pedido = req.TrasforPedido()

        nome_LocalOrigem.text = pedido.nomeOrigem
        localorigem.text = pedido.LocalOrigen
        nomeLocalDestino.text = pedido.nomeDestino
        localDestino.text = pedido.LocalDestino



        botaoRota.setOnClickListener(View.OnClickListener {
            val uri = Uri.parse(CriadorDeUriLocal().creatUri(pedido))
            val intent = Intent(Intent.ACTION_VIEW,uri)
            intent.setPackage("com.google.android.apps.maps")
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        })

    }



    override fun run() {
        MainActivity().enviaLocalizacao()
        handler.postDelayed(this, 50000)
    }


}