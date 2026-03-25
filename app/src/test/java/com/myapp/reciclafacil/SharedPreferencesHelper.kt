package com.myapp.reciclafacil

import android.content.Context

// Esta classe simula as funções de SharedPreferences que estavam na MainActivity.
// No teste, o Context será mockado.
class SharedPreferencesHelper(
    private val context: Context,
    private val prefsName: String,
    private val keyCidade: String
) {
    fun buscarCidadeSelecionada(): String {
        val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        return prefs.getString(keyCidade, "") ?: ""
    }

    fun saveSelectedCity(city: String) {
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit()
            .putString(keyCidade, city)
            .apply()
    }
}