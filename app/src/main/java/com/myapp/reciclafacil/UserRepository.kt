package com.myapp.reciclafacil

import android.content.Context
import android.content.SharedPreferences

class UserRepository(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Salva o cadastro do usuário
    fun registerUser(email: String, name: String, password: String): Boolean {
        return sharedPreferences.edit().apply {
            putString("USER_EMAIL", email)
            putString("USER_NAME", name)
            putString("USER_PASSWORD", password)
            putBoolean("IS_LOGGED_IN", false) // Cadastro não loga automaticamente
        }.commit()
    }

    // Valida se as credenciais de login estão corretas
    fun loginUser(email: String, password: String): Boolean {
        val savedEmail = sharedPreferences.getString("USER_EMAIL", null)
        val savedPassword = sharedPreferences.getString("USER_PASSWORD", null)

        return if (email == savedEmail && password == savedPassword) {
            sharedPreferences.edit().putBoolean("IS_LOGGED_IN", true).apply()
            true
        } else {
            false
        }
    }

    // Verifica se o usuário já está logado (útil para a Splash Screen)
    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false)
    }

    // Faz o logout do usuário
    fun logoutUser() {
        sharedPreferences.edit().putBoolean("IS_LOGGED_IN", false).apply()
    }

    // Obtém o nome do usuário para a tela de Perfil
    fun getUserName(): String {
        return sharedPreferences.getString("USER_NAME", "Usuário") ?: "Usuário"
    }
}