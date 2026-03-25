package com.myapp.reciclafacil

// Define o modelo de dados completo para um local de reciclagem
data class RecyclingLocation(
    val nome: String,
    val endereco: String,
    val imagemResourceId: Int, // ID da imagem drawable (ex: R.drawable.fachada_biguaçu)
    val horarioFuncionamento: String, // Novo: Ex: "Segunda a Sábado, 8h às 18h"
    val telefone: String,             // Novo: Ex: "(48) 99999-0000"
    val materiaisAceitos: List<String> // Novo: Lista de materiais aceitos
)