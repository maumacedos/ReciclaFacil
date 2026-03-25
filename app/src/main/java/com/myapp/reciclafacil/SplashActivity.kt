package com.myapp.reciclafacil // Seu pacote

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000 // 3 segundos

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 1. Opcional: Adicionar Animação ao Logo
        val logo = findViewById<android.widget.ImageView>(R.id.logo_animacao)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in) // Crie fade_in.xml
        logo.startAnimation(fadeIn)


        // 2. Controla o tempo e a navegação
        Handler().postDelayed({
            // Cria a Intent para ir para a MainActivity
            val i = Intent(this, CitySelectionActivity::class.java)
            startActivity(i)

            // Termina a SplashActivity para que o botão 'voltar' não a traga de volta
            finish()
        }, SPLASH_TIME_OUT)
    }
}