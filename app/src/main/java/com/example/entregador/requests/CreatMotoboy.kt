package com.example.entregador.requests

import android.os.AsyncTask
import com.example.entregador.model.Motoboy
import com.example.entregador.urls.Url
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.IOException

class CreatMotoboy (var m: Motoboy): AsyncTask<Void, Void, String>()  {



    override fun doInBackground(vararg p0: Void?): String {
        // Crie uma instância da sua classe para representar os dados que você deseja enviar
        val dataToSend = Motoboy(m.nome,m.telefone,m.email,m.senha,m.cpf,m.nomeParente,m.telefoneEmergencia,m.chavepix)

        // Use a biblioteca Gson para converter a classe em JSON
        val gson = Gson()
        val jsonData = gson.toJson(dataToSend)

        // Crie uma instância do cliente OkHttp
        val client = OkHttpClient()

        // Especifique a URL do servidor
        val url = "${Url().urlRoot}/crear/motoboy"

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
                return "${response.code}"
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