package com.example.entregador.historico

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.entregador.R
import com.example.entregador.model.HistoricoAno

class HistoricoAnoAdapter(val historicoAno:List<HistoricoAno>,val token:String?,val context: Context) :RecyclerView.Adapter<HistoricoAnoAdapter.HistoricoAnoViewHoder>() {


    inner class HistoricoAnoViewHoder(itemView: View):RecyclerView.ViewHolder(itemView){
        val ano = itemView.findViewById<TextView>(R.id.anoTextView)
        val historicoMes = itemView.findViewById<RecyclerView>(R.id.subHistoricoAnoRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoAnoViewHoder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.historico_ano,parent,false)
        return HistoricoAnoViewHoder(view)
    }

    override fun getItemCount(): Int {
        return  historicoAno.size
    }

    override fun onBindViewHolder(holder: HistoricoAnoViewHoder, position: Int) {
        val historicoMes = historicoAno[position]

        holder.ano.text = historicoMes.ano

        holder.historicoMes.setHasFixedSize(true)
        holder.historicoMes.layoutManager = LinearLayoutManager(holder.itemView.context)
        val adapter = HistoricoAdapter(historicoMes.mesesComHistoricos,context,token)
        holder.historicoMes.adapter= adapter
    }

}