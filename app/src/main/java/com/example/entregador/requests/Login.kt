package com.example.entregador.requests

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.entregador.model.LoginDados
import com.example.entregador.model.Motoboy
import com.example.entregador.urls.Url
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.IOException

class Login(val log: LoginDados) : AsyncTask<Void, Void, String>(){

    override fun doInBackground(vararg p0: Void?): String {
        val dataToSend = LoginDados(log.username,log.password)

        // Use a biblioteca Gson para converter a classe em JSON
        val gson = Gson()
        val jsonData = gson.toJson(dataToSend)

        // Crie uma instância do cliente OkHttp
        val client = OkHttpClient()

        // Especifique a URL do servidor
        val url = "${Url().urlRoot}/login"

        // Crie um corpo de solicitação com o JSON
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonData)


        // Crie uma solicitação POST com a URL e o corpo da solicitação
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()


        try {
            // Execute a solicitação e obtenha a resposta
            val response = client.newCall(request).execute()

            // Verifique se a solicitação foi bem-sucedida (código de resposta 200)
            if (response.isSuccessful) {
                // Obtenha a resposta como string
                val responseBodyString = response.body?.string() ?: "Resposta vazia"

                return responseBodyString
            } else {
                // Se a solicitação não for bem-sucedida, retorne uma mensagem de erro
                val errorBodyString = response.body?.string() ?: "Resposta vazia"

                return "Erro: ${response.code}"
            }
        } catch (e: IOException) {
            // Em caso de exceção, retorne uma mensagem de erro
            return "Erro: ${e.message}"
        }
    }


}