package com.myapp.reciclafacil

import android.os.Bundle
import android.widget.TextView // Importação necessária para manipular o TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.content.pm.PackageManager // ⬅️ Essencial para ler o package info

class DevelopersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Garante o modo claro, seguindo o padrão do app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers)

        // Opcional: Configurar o botão de retorno nativo (seta)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Desenvolvedores"

        // 🎯 LÓGICA DINÂMICA: LER E EXIBIR A VERSÃO DO APLICATIVO
        try {
            // 1. Obtém as informações do pacote atual
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            val versionName = pInfo.versionName // Lê a string da versão (ex: "1.0")

            // 2. Encontra o TextView no layout (Você precisa ter o ID R.id.text_app_version no seu XML)
            val tvVersion = findViewById<TextView>(R.id.text_app_version)

            // 3. Define o texto dinâmico
            tvVersion.text = "Versão $versionName"

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            // Caso o sistema não consiga encontrar a versão (erro raro), exibe um valor padrão
            val tvVersion = findViewById<TextView>(R.id.text_app_version)
            tvVersion.text = "Versão Desconhecida"
        }
    }

    // Configura o botão de retorno nativo para fechar a tela
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}