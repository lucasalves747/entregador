package com.example.entregador.model

import java.math.BigDecimal

class PedidoDetalhado (


    val nomeEstabelecimento: String,
    val localOrigem:String,
    val localDestino:String,
    val valor : BigDecimal,
    val observacao: String,
    val itensDoPedido:String,
    var status:String,

    )
