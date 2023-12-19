package com.example.entregador

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.entregador.model.Pedido
import com.example.entregador.model.PedidoDetalhado
import okhttp3.Call
import okhttp3.Response
import android.os.AsyncTask
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.entregador.model.Localizacao
import com.example.entregador.model.LoginDados
import com.example.entregador.model.Motoboy
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
class Requests {
    val gson = Gson()

    public fun get(url: String): String {

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        var body = String()

        client.newCall(request).enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace();
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.i("Response", "Recever mendsdgj")

                    response.use {
                        if (!response.isSuccessful) {
                            Log.e("HTTP erro", "algo nao carregou ou nao foi bem sucedido")
                        } else {
                            val pedido = gson.fromJson(response.body?.string(),Pedido::class.java)
                        }
                    }
                }
            })
        return body
    }

    public fun post() {
        val gson = Gson()

        val json = gson.toJson(/*usuario*/null, Motoboy::class.java)

        val token = "SeuTokenAqui"

        // URL do endpoint que você deseja chamar
        val url = "https://sua-api.com/exemplo-endpoint"


        val client = OkHttpClient()

        // Criar um corpo de requisição usando o JSON e o tipo de mídia apropriado
        val requestBody = json.toRequestBody("application/json".toMediaType())

        // Criar a requisição POST com o corpo e o cabeçalho de autorização
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", "Bearer $token")
            .build()

        // Executar a requisição
        val response = client.newCall(request).execute()

        // Processar a resposta
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            println("Requisição bem-sucedida. Resposta: $responseBody")
        } else {
            println("Erro na requisição. Código de resposta: ${response.code}")
        }
    }


    public fun TrasforPedido(): PedidoDetalhado {
        val body = get("http://")
        return gson.fromJson(body, PedidoDetalhado::class.java)
    }



    fun pushLocalizacao(localizacao: Localizacao) {

            val gson = Gson()

            val json = gson.toJson(localizacao, Localizacao::class.java)

           // val token = MainActivity().Token

            // URL do endpoint que você deseja chamar
            val url = "https://mgdexpressapi-production.up.railway.app/"

            val client = OkHttpClient()

            // Criar um corpo de requisição usando o JSON e o tipo de mídia apropriado
            val requestBody = json.toRequestBody("application/json".toMediaType())

            // Criar a requisição POST com o corpo e o cabeçalho de autorização
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                //.addHeader("Authorization", "Bearer $token")
                .build()

            // Executar a requisição
            val response = client.newCall(request).execute()

            // Processar a resposta
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                println("Requisição bem-sucedida. Resposta: $responseBody")
            } else {
                println("Erro na requisição. Código de resposta: ${response.code}")
            }

    }


}