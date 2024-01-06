package com.example.entregador.historico

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.entregador.DetalhesEntrega
import com.example.entregador.DetalhesHistorico
import com.example.entregador.R
import com.example.entregador.model.Pedido
import com.example.entregador.model.SubListaHistoricoDados

class SubHistoricoAdapter(private val subListHistorico:List<SubListaHistoricoDados>,val context: Context,val token:String?):RecyclerView.Adapter<SubHistoricoAdapter.SubHistoricoListViewHoder>() {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("dd-MM-yyyy")

    inner class SubHistoricoListViewHoder(itemView:View):RecyclerView.ViewHolder(itemView){
        val data = itemView.findViewById<TextView>(R.id.textViewDate)
        val nomeEstabelecimento =  itemView.findViewById<TextView>(R.id.textViewEstablishmentName)
        val botao = itemView.findViewById<Button>(R.id.buttonDetails)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubHistoricoListViewHoder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entregas_itens_historico,parent,false)
        return SubHistoricoListViewHoder(view)
    }

    override fun getItemCount(): Int {
       return subListHistorico.size
    }

    override fun onBindViewHolder(holder: SubHistoricoListViewHoder, position: Int) {
        val subHitorico = subListHistorico[position]
        holder.botao.setOnClickListener {
            irparaASegundaTela(subHitorico)
        }
        val date = inputFormat.parse(subHitorico.dataEntrega)
        holder.data.text = outputFormat.format(date)
        holder.nomeEstabelecimento.text = subHitorico.nomeStabelecimento

    }

    fun irparaASegundaTela(hitorico: SubListaHistoricoDados){

        val segundaTela = Intent(context, DetalhesHistorico::class.java)
        segundaTela.putExtra("id","${hitorico.id}")
        segundaTela.putExtra("token",token)
        ContextCompat.startActivity(context, segundaTela, null)
    }
}