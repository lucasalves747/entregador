package com.example.entregador.requests

import android.os.AsyncTask
import okhttp3.*
import java.io.IOException

class ReqPedidodsPendents(val authToken:String?) : AsyncTask<Void, Void, String>()  {
    override fun doInBackground(vararg p0: Void?): String {
        val client = OkHttpClient()

        // Crie uma solicitação GET com a URL
        val request = Request.Builder()
            .url("https://mgdexpressapi-production.up.railway.app/pedidos/pendente")
            .get()
            .addHeader("Authorization", "Bearer ${authToken}")
            .addHeader("Content-Type", "application/json")
            .build()


        try {
            // Execute a solicitação e obtenha a resposta
            val response = client.newCall(request).execute()

            // Verifique se a solicitação foi bem-sucedida (código de resposta 200)
            if (response.isSuccessful) {
                // Obtenha a resposta como string
                return response.body?.string() ?: "Resposta vazia"
            } else {
                // Se a solicitação não for bem-sucedida, retorne uma mensagem de erro
                throw Exception("Erro: ${response.code}")
            }
        } catch (e: IOException) {
            // Em caso de exceção, retorne uma mensagem de erro
            return "Erro: ${e.message}"
        }


    }
}