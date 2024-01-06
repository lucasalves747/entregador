package com.example.entregador.model

// OuterItem.kt
data class ListaHistorico(val mes: String,val totalRecebido:Float, val historicos: List<SubListaHistoricoDados>)

// InnerItem.kt
data class SubListaHistoricoDados(val id:Long, val dataEntrega: String,val nomeStabelecimento:String,val valor:Long)

data class HistoricoAno(val ano: String, val mesesComHistoricos: List<ListaHistorico>)

/*[
 {
   "mes": "JANUARY",
   "TotalRecebido": 0,
   "historico": [
     {
       "id": 0,
       "dataEntrega": "2023-12-25",
       "nomeStabelecimento": "string",
       "valor": 0
     }
   ]
 }
]*/