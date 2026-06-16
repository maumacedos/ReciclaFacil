package com.myapp.reciclafacil

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class RegisterActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Força a barra de status a ficar preta para manter o padrão visual do app
        window.statusBarColor = android.graphics.Color.BLACK

        // Inicializa o mesmo ViewModel compartilhado de autenticação
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Mapeia os componentes do layout XML
        val etName = findViewById<EditText>(R.id.etRegisterName)
        val etEmail = findViewById<EditText>(R.id.etRegisterEmail)
        val etPassword = findViewById<EditText>(R.id.etRegisterPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etRegisterConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)

        // Configura o clique do botão de Cadastrar
        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            // Dispara as validações e a tentativa de salvar dados no ViewModel
            authViewModel.register(name, email, password, confirmPassword)
        }

        // Volta para a tela de login se o usuário clicar no texto inferior
        tvBackToLogin.setOnClickListener {
            finish()
        }

        // OBSERVADORES (MVVM)

        // 1. Observa se o cadastro deu certo
        authViewModel.registerResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Cadastro realizado! Faça login para entrar.", Toast.LENGTH_LONG).show()
                finish() // Fecha a tela de cadastro e volta automaticamente para o Login
            }
        }

        // 2. Observa se houve alguma mensagem de erro (ex: senhas diferentes)
        authViewModel.errorMessage.observe(this) { message ->
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}