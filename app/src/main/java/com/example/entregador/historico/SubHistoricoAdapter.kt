package com.example.entregador.historico

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.entregador.R
import com.example.entregador.model.SubListaHistoricoDados

class SubHistoricoAdapter(private val subListHistorico:List<SubListaHistoricoDados>):RecyclerView.Adapter<SubHistoricoAdapter.SubHistoricoListViewHoder>() {

    inner class SubHistoricoListViewHoder(itemView:View):RecyclerView.ViewHolder(itemView){
        val data = itemView.findViewById<TextView>(R.id.textViewDate)
        val nomeEstabelecimento =  itemView.findViewById<TextView>(R.id.textViewEstablishmentName)
        val localEntrega = itemView.findViewById<TextView>(R.id.textViewDeliveryLocation)
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
        holder.data.text = subHitorico.data
        holder.nomeEstabelecimento.text = subHitorico.nomeEstabelecimento
        holder.localEntrega.text = subHitorico.localDaEntrega
    }
}