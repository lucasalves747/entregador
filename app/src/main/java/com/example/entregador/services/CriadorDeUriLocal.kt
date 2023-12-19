package com.example.entregador.services

import com.example.entregador.model.PedidoDetalhado

class CriadorDeUriLocal {
    fun creatUri(pedido: PedidoDetalhado):String{
        val localOrigem = pedido.LocalOrigen?.replace(" ","+",true)

        val locaDestino = pedido.LocalDestino?.replace(" ","+",true)

        return "https://www.google.com/maps/dir/?api=1&origin="+localOrigem+"&destination="+locaDestino+ "&travelmode=driving"
    }
}