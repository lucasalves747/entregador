package com.example.entregador.historico

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.entregador.MainActivity
import com.example.entregador.R
import com.example.entregador.model.HistoricoAno
import com.example.entregador.model.ListaHistorico
import com.example.entregador.model.Pedido
import com.example.entregador.model.SubListaHistoricoDados
import com.example.entregador.requests.RequestHistorico
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Historico () : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)

        val token = intent.getStringExtra("token")

        val botaoLista = findViewById<ImageButton>(R.id.botaolistagem)

        botaoLista.setOnClickListener{
                finish()
            }

        recyclerView = findViewById(R.id.recyclerViewHistorico)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //var h = historicoFromJson(RequestHistorico(token).execute().get())

       var h = historicoFromJson(RequestHistorico(token).execute().get())

        val adapter = HistoricoAnoAdapter(h,token,this)
        recyclerView.adapter = adapter

    }



    private fun historicoFromJson(json: String?): List<HistoricoAno> {
        val gson = Gson()
        val listaTipo = object : TypeToken<List<HistoricoAno>>() {}.type
        return gson.fromJson(json, listaTipo)

    }



}