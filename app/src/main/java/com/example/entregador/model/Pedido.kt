package com.example.entregador.model

import java.math.BigDecimal

class Pedido (
    val id: Long,
    val valor : BigDecimal,
    val localDestino: String,
    val nomeEstabelecimento: String,
    val status : String


)
