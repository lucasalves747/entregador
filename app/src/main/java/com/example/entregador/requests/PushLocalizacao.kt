package com.example.entregador.requests

import android.os.AsyncTask

import com.example.entregador.model.Localizacao
import com.example.entregador.urls.Url
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.IOException

class LocalizacaoDados(var localizacao: Localizacao) {}
class PushLocalizacao(val latitude: Double, val longitude: Double,val authToken:String?) : AsyncTask<Void, Void, String>(){


    override fun doInBackground(vararg p0: Void?): String {
        val dataToSend = LocalizacaoDados( Localizacao(latitude,longitude))

        // Use a biblioteca Gson para converter a classe em JSON
        val gson = Gson()
        val jsonData = gson.toJson(dataToSend)



        // Crie uma instância do cliente OkHttp
        val client = OkHttpClient()

        // Especifique a URL do servidor
        val url = "${Url().urlRoot}/motoboy/localizacao"

        // Crie um corpo de solicitação com o JSON
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonData)


        // Crie uma solicitação POST com a URL e o corpo da solicitação
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", "Bearer ${authToken}")
            .addHeader("Content-Type", "application/json")
            .build()


        try {
            // Execute a solicitação e obtenha a resposta
            val response = client.newCall(request).execute()

            // Verifique se a solicitação foi bem-sucedida (código de resposta 200)
            if (response.isSuccessful) {
                // Obtenha a resposta como string

                return jsonData
            } else {
                // Se a solicitação não for bem-sucedida, retorne uma mensagem de erro
                val errorBodyString = response.body?.string() ?: "Resposta vazia"

                return "Erro: ${response.code}"
            }
        } catch (e: IOException) {
            // Em caso de exceção, retorne uma mensagem de erro
            return "Algo saiu mal"
        }
    }
}