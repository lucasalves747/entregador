package com.example.entregador.model

import java.math.BigDecimal

class PedidoDetalhado (
    val nomeOrigem: String,
    val LocalOrigen:String,
    val nomeDestino:String,
    val LocalDestino:String,
    val valor : BigDecimal,
    val observacoes: String,
    val itensPedido:String

)
