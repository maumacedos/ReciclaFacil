package com.myapp.reciclafacil

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Força a barra de status a ficar preta mantendo a coerência visual
        window.statusBarColor = android.graphics.Color.BLACK

        // Inicializa o ViewModel compartilhado
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Mapeia os elementos do XML
        val etForgotEmail = findViewById<EditText>(R.id.etForgotEmail)
        val btnRecoverPassword = findViewById<Button>(R.id.btnRecoverPassword)
        val tvBackToLoginFromForgot = findViewById<TextView>(R.id.tvBackToLoginFromForgot)

        // Clique do botão de recuperação
        btnRecoverPassword.setOnClickListener {
            val email = etForgotEmail.text.toString().trim()

            // Dispara a simulação de recuperação no ViewModel
            val success = authViewModel.recoverPassword(email)
            if (success) {
                Toast.makeText(this, "Instruções enviadas para o e-mail informado!", Toast.LENGTH_LONG).show()
                finish() // Fecha a tela e volta para a tela de Login
            }
        }

        // Clique para fechar a tela e voltar ao Login
        tvBackToLoginFromForgot.setOnClickListener {
            finish()
        }

        // Observa as mensagens de erro de validação (ex: campo vazio)
        authViewModel.errorMessage.observe(this) { message ->
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}