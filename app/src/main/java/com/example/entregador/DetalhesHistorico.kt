package com.example.entregador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.entregador.model.DetalhesHistorico
import com.example.entregador.requests.RequesDetalhesHistorico
import com.google.gson.Gson

class DetalhesHistorico : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_historico)

        var token = intent.getStringExtra("token")
        var id = intent.getStringExtra("id")


        var nomeEstabelecimento = findViewById<TextView>( R.id.nomeEstabelecimento)
        var localOrigem= findViewById<TextView>( R.id.localOrigem)
        var observacao= findViewById<TextView>( R.id.observacao)
        var valor= findViewById<TextView>( R.id.valor)
        var itensDoPedido= findViewById<TextView>( R.id.itensDoPedido)
        var localDestino= findViewById<TextView>( R.id.localDestino)
        var dataCriacao= findViewById<TextView>( R.id.dataCriacao)
        var dataEntrega= findViewById<TextView>( R.id.dataEntrega)
        var nomeGerente= findViewById<TextView>( R.id.nomeGerente)
        var telefoneGerente= findViewById<TextView>( R.id.telefoneGerente)
        var emailGerente= findViewById<TextView>( R.id.emailGerente)

        var historico =jsonTransform(RequesDetalhesHistorico(token,id).execute().get())

        nomeEstabelecimento.text = historico.nomeEstebelecimento
        localOrigem.text = historico.localEstabelecimento
        localDestino.text = historico.LocalDestino
        observacao.text = historico.observacao
        valor.text = historico.valor.toString()
        itensDoPedido.text = historico.itensDoPedido
        dataCriacao.text = historico.dataCriacao
        dataEntrega.text = historico.dataEntrega
        nomeGerente.text =  historico.nome
        telefoneGerente.text = historico.telefone
        emailGerente.text = historico.email


    }

    private fun jsonTransform(json:String):DetalhesHistorico{
        var gson = Gson()

        return gson.fromJson<DetalhesHistorico>(json,DetalhesHistorico::class.java)
    }
}