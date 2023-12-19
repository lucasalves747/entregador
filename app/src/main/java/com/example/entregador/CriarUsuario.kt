package com.example.entregador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.entregador.model.Motoboy
import com.example.entregador.requests.CreatMotoboy

class CriarUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_usuario)

        val nome = findViewById<EditText>(R.id.criarUsuario_nome_Edit)
        val email = findViewById<EditText>(R.id.criarUsuario_nome_Edit)
        val senha = findViewById<EditText>(R.id.criarUsuario_senha_Edit)
        val telefone = findViewById<EditText>(R.id.criarUsuario_telefone_Edit)
        val cpf = findViewById<EditText>(R.id.criarUsuario_cpf_Edit)
        val grauDeparentescoEmergencia = findViewById<EditText>(R.id.criarUsuario_grauParentesco_Edit)
        val telefoneEmergencia = findViewById<EditText>(R.id.criarUsuario_telefoneEmergencia_Edit)
        val pix = findViewById<EditText>(R.id.criarUsuario_pix_Edit)
        val btn = findViewById<Button>(R.id.botaoCadastrar)

        btn.setOnClickListener {
            val usuario = Motoboy(nome.text.toString(),telefone.text.toString(),email.text.toString(),senha.text.toString(),cpf.text.toString(),grauDeparentescoEmergencia.text.toString(),telefoneEmergencia.text.toString(),pix.text.toString())


            CreatMotoboy(usuario).execute()

            val segundaTela = Intent(this, Login::class.java)
            ContextCompat.startActivity(this, segundaTela, null)
        }
    }
}