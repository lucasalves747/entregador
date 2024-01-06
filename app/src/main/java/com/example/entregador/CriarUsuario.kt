package com.example.entregador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.Colors
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
        val email = findViewById<EditText>(R.id.criarUsuario_email_Edit)
        val senha = findViewById<EditText>(R.id.criarUsuario_senha_Edit)
        val telefone = findViewById<EditText>(R.id.criarUsuario_telefone_Edit)
        val cpf = findViewById<EditText>(R.id.criarUsuario_cpf_Edit)
        val grauDeparentescoEmergencia = findViewById<EditText>(R.id.criarUsuario_grauParentesco_Edit)
        val telefoneEmergencia = findViewById<EditText>(R.id.criarUsuario_telefoneEmergencia_Edit)
        val pix = findViewById<EditText>(R.id.criarUsuario_pix_Edit)
        val btn = findViewById<Button>(R.id.botaoCadastrar)

        btn.setOnClickListener {
            if(!(nome.text.isBlank())&&!(telefone.text.isBlank())&&!(email.text.isBlank())&&!(senha.text.isBlank())&&!(cpf.text.isBlank())&&!(grauDeparentescoEmergencia.text.isBlank())&&!(telefoneEmergencia.text.isBlank())&&!(pix.text.isBlank())) {
                val usuario = Motoboy(
                    nome.text.toString(),
                    telefone.text.toString(),
                    email.text.toString(),
                    senha.text.toString(),
                    cpf.text.toString(),
                    grauDeparentescoEmergencia.text.toString(),
                    telefoneEmergencia.text.toString(),
                    pix.text.toString()
                )

                val res = CreatMotoboy(usuario).execute().get()

                if(res.equals("200")) {
                    val segundaTela = Intent(this, Login::class.java)
                    ContextCompat.startActivity(this, segundaTela, null)
                }
                else if(res.equals("245")){
                    email.setTextColor(resources.getColor(R.color.vermelho))
                    email.setText("email ja existe")
                }

            }else{
                if(nome.text.isBlank()){
                    nome.setTextColor(resources.getColor(R.color.vermelho))
                    nome.setText("*Obrigatorio")
                }
                if(telefone.text.isBlank()){
                    telefone.setTextColor(resources.getColor(R.color.vermelho))
                    telefone.setText("*Obrigatorio")
                }
                if(email.text.isBlank()){
                    email.setTextColor(resources.getColor(R.color.vermelho))
                    email.setText("*Obrigatorio")
                }
                if(senha.text.isBlank()){
                    senha.setTextColor(resources.getColor(R.color.vermelho))
                    senha.setText("*Obrigatorio")
                }
                if(cpf.text.isBlank()){
                    cpf.setTextColor(resources.getColor(R.color.vermelho))
                    cpf.setText("*Obrigatorio")
                }
                if(grauDeparentescoEmergencia.text.isBlank()){
                    grauDeparentescoEmergencia.setTextColor(resources.getColor(R.color.vermelho))
                    grauDeparentescoEmergencia.setText("*Obrigatorio")
                }
                if(telefoneEmergencia.text.isBlank()){
                    telefoneEmergencia.setTextColor(resources.getColor(R.color.vermelho))
                    telefoneEmergencia.setText("*Obrigatorio")
                }
                if(pix.text.isBlank()) {
                    pix.setTextColor(resources.getColor(R.color.vermelho))
                    pix.setText("*Obrigatorio")
                }

                }

        }

        email.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                email.setTextColor(resources.getColor(R.color.black))
                email.setText("")
            }
        }

        nome.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                nome.setTextColor(resources.getColor(R.color.black))
                nome.setText("")
            }
        }

            telefone.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    telefone.setTextColor(resources.getColor(R.color.black))
                    telefone.setText("")
                }
            }

            senha.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    senha.setTextColor(resources.getColor(R.color.black))
                    senha.setText("")
                }
            }

                cpf.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        cpf.setTextColor(resources.getColor(R.color.black))
                        cpf.setText("")
                    }
                }

                grauDeparentescoEmergencia.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        grauDeparentescoEmergencia.setTextColor(resources.getColor(R.color.black))
                        grauDeparentescoEmergencia.setText("")
                    }
                }

                    telefoneEmergencia.setOnFocusChangeListener { _, hasFocus ->
                        if (hasFocus) {
                            telefoneEmergencia.setTextColor(resources.getColor(R.color.black))
                            telefoneEmergencia.setText("")
                        }
                    }
                    pix.setOnFocusChangeListener { _, hasFocus ->
                        if (hasFocus) {
                            pix.setTextColor(resources.getColor(R.color.black))
                            pix.setText("")
                        }
                    }


    }
}