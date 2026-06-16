package com.myapp.reciclafacil

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000 // 2 segundos

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Força a barra de status a ficar preta para combinar com a Splash animada
        window.statusBarColor = android.graphics.Color.BLACK

        // 1. Adicionar Animação ao Logo
        val logo = findViewById<android.widget.ImageView>(R.id.logo_animacao)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        logo.startAnimation(fadeIn)

        // Inicializa o gerenciador de usuários (Model)
        val userRepository = UserRepository(this)

        // 2. Controla o tempo e a tomada de decisão da navegação (MVVM)
        Handler().postDelayed({

            // Decisão inteligente de navegação baseada no estado do usuário
            val intentDestino = if (userRepository.isUserLoggedIn()) {
                // Se já estiver logado, segue o fluxo normal do app (Escolha de cidade)
                Intent(this, CitySelectionActivity::class.java)
            } else {
                // Se não estiver logado, obriga a passar pela tela de Login
                Intent(this, LoginActivity::class.java)
            }

            startActivity(intentDestino)

            // Termina a SplashActivity para que o botão 'voltar' não a traga de volta
            finish()
        }, SPLASH_TIME_OUT)
    }
}