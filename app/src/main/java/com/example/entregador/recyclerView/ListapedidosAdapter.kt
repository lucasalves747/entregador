package com.example.entregador.recyclerView

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.entregador.DetalhesEntrega
import com.example.entregador.MainActivity
import com.example.entregador.R
import com.example.entregador.model.Pedido

class ListapedidosAdapter(
    private val context: Context,
    private var pedidos: List<Pedido>
) : RecyclerView.Adapter<ListapedidosAdapter.viewHoder>() {

    class viewHoder(view: View): RecyclerView.ViewHolder(view) {
        val botao = itemView.findViewById<Button>(R.id.button)
        fun vincula(pedido: Pedido) {
            val valor = itemView.findViewById<TextView>(R.id.valorText)
            val local = itemView.findViewById<TextView>(R.id.localText)
            val nome = itemView.findViewById<TextView>(R.id.nomeText)
            valor.text = pedido.valor.toPlainString()
            local.text = pedido.local
            nome.text = pedido.Estabelecimento
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHoder {
        val inflate = LayoutInflater.from(context)
        val view = inflate.inflate(R.layout.solicitacao_pedido,parent,false)
        return viewHoder(view)
    }

    override fun getItemCount(): Int = pedidos.size

    override fun onBindViewHolder(holder: viewHoder, position: Int) {
        val pedido = pedidos[position]
        holder.vincula(pedido)

        holder.botao.setOnClickListener(View.OnClickListener() {
          irparaASegundaTela(pedido)
        })
    }

    fun irparaASegundaTela(pedido: Pedido){
        MainActivity().setFalseExecutaAtualizacaoDelocalizacaoEpedidos()
        val segundaTela = Intent(context,DetalhesEntrega::class.java)
        segundaTela.putExtra("id",pedido.id)
        startActivity(context,segundaTela,null)
    }

    fun atualizarLista() {
        notifyDataSetChanged()
    }

}
