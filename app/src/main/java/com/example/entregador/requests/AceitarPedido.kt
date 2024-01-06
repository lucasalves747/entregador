package com.example.entregador.requests

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import com.example.entregador.model.Motoboy
import com.example.entregador.services.CriadorDeUriLocal
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class AceitarPedido (var authToken:String?,val url :String) : AsyncTask<Void, Void, String>(){

    override fun doInBackground(vararg p0: Void?): String {
        val client = OkHttpClient()

        // Crie uma solicitação GET com a URL
        val request = Request.Builder()
            .url(url)
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
                return "Erro: ${response.code}"
            }
        } catch (e: IOException) {
            // Em caso de exceção, retorne uma mensagem de erro
            return "Erro: ${e.message}"
        }


    }
}