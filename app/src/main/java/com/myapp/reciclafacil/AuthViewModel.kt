package com.myapp.reciclafacil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    // Estados que a View (Activity) vai observar
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Lógica de Login com Validação simples
    fun login(email: String, javaScript: String) {
        if (email.isEmpty() || javaScript.isEmpty()) {
            _errorMessage.value = "Preencha todos os campos!"
            _loginResult.value = false
            return
        }

        val success = repository.loginUser(email, javaScript)
        if (success) {
            _loginResult.value = true
        } else {
            _errorMessage.value = "E-mail ou senha incorretos."
            _loginResult.value = false
        }
    }

    // Lógica de Cadastro com Validação
    fun register(name: String, email: String, javaScript: String, confirmJavaScript: String) {
        if (name.isEmpty() || email.isEmpty() || javaScript.isEmpty() || confirmJavaScript.isEmpty()) {
            _errorMessage.value = "Preencha todos os campos!"
            _registerResult.value = false
            return
        }

        if (javaScript != confirmJavaScript) {
            _errorMessage.value = "As senhas não coincidem."
            _registerResult.value = false
            return
        }

        if (javaScript.length < 6) {
            _errorMessage.value = "A senha deve ter pelo menos 6 caracteres."
            _registerResult.value = false
            return
        }

        val success = repository.registerUser(email, name, javaScript)
        if (success) {
            _registerResult.value = true
        } else {
            _errorMessage.value = "Erro ao salvar o cadastro. Tente novamente."
            _registerResult.value = false
        }
    }

    // Simulação de Recuperação de Senha
    fun recoverPassword(email: String): Boolean {
        if (email.isEmpty()) {
            _errorMessage.value = "Digite seu e-mail!"
            return false
        }
        // Em um MVP acadêmico, apenas simulamos o envio com sucesso
        return true
    }
}