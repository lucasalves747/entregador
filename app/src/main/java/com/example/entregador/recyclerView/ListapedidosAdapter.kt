package com.example.entregador.recyclerView

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.entregador.DetalhesEntrega
import com.example.entregador.MainActivity
import com.example.entregador.R
import com.example.entregador.model.Pedido

class ListapedidosAdapter(
    private val context: Context,
    private var pedidos: List<Pedido>,
    val token: String?
) : RecyclerView.Adapter<ListapedidosAdapter.viewHoder>() {

    public var contadorImagem = 1;

    class viewHoder(view: View): RecyclerView.ViewHolder(view) {
        val botao = itemView.findViewById<Button>(R.id.button)
        val imagem = itemView.findViewById<ImageView>(R.id.imageView)


        fun vincula(pedido: Pedido) {
            val valor = itemView.findViewById<TextView>(R.id.valorText)
            val local = itemView.findViewById<TextView>(R.id.localText)
            val nome = itemView.findViewById<TextView>(R.id.nomeText)

            valor.text = pedido.valor.toPlainString()
            if(pedido.localDestino.length>18) {
                local.text = pedido.localDestino.substring(0, 18)+"..."
            }
            else{local.text = pedido.localDestino}
            nome.text = pedido.nomeEstabelecimento
            if(pedido.status == "INICIAR"){
                botao.text = "aceitar"
            }
            else {
                botao.text = "continuar"
            }



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

        holder.botao.setOnClickListener{
          irparaASegundaTela(pedido)
        }
        var imagem = holder.imagem

        when (contadorImagem) {
            1 -> {
                imagem.setImageResource(R.drawable.img)
                contadorImagem++
            }
            2 -> {
                imagem.setImageResource(R.drawable.img_2)
                contadorImagem++
            }
            3 -> {
                imagem.setImageResource(R.drawable.img_3)
                contadorImagem++
            }
            4 -> {
                imagem.setImageResource(R.drawable.img_4)
                contadorImagem++
            }
            5 -> {
                imagem.setImageResource(R.drawable.img_5)
                contadorImagem++
            }
            6 -> {
                imagem.setImageResource(R.drawable.img_6)
                contadorImagem++
            }
            7 -> {
                imagem.setImageResource(R.drawable.img_7)
                contadorImagem++
            }
            8 -> {
                imagem.setImageResource(R.drawable.img_8)
                contadorImagem++
            }
            else -> {
                imagem.setImageResource(R.drawable.img_9)
                contadorImagem = 0
            }
        }
    }

    fun irparaASegundaTela(pedido: Pedido){

        val segundaTela = Intent(context,DetalhesEntrega::class.java)
        segundaTela.putExtra("id","${pedido.id}")
        segundaTela.putExtra("token",token)
        startActivity(context,segundaTela,null)
    }

    fun atualizarLista() {
        notifyDataSetChanged()
    }

}
