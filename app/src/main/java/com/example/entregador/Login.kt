package com.example.entregador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.entregador.model.LoginDados
import com.example.entregador.requests.Login


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val textEmailOusenhaInvalidos = findViewById<TextView>(R.id.textViewSenhaInvalida)
        val email = findViewById<EditText>(R.id.editTextEmail)
        val senha = findViewById<EditText>(R.id.editTextPassword)
        val botao = findViewById<Button>(R.id.buttonLogin)
        val cadastrar = findViewById<TextView>(R.id.textViewSignUp)


        botao.setOnClickListener {
            val res =
                Login(LoginDados(email.text.toString(), senha.text.toString())).execute()
                    .get()


            if(res.equals("Erro: 403")){
                textEmailOusenhaInvalidos.text = "email ou senha invalidos"
                
            }
            else {

                val sharedPreferences =
                    this.getSharedPreferences("chave-token", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("token", res)
                editor.apply()

                val segundaTela = Intent(this, MainActivity::class.java)
                ContextCompat.startActivity(this, segundaTela, null)
            }


        }

    }
}