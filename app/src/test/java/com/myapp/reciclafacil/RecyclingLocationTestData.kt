package com.myapp.reciclafacil.test

import com.myapp.reciclafacil.RecyclingLocation

// Objeto para fornecer dados de teste consistentes
object RecyclingLocationTestData {
    // Valores padrão válicos para RecyclingLocation (para evitar TODO())
    private const val DEFAULT_INT = 0
    private const val DEFAULT_STRING = ""
    private val DEFAULT_LIST = emptyList<String>()

    val todosLocais = listOf(
        RecyclingLocation(
            nome = "Recicla Fácil",
            endereco = "Rua do Teste, 100, Centro - Biguaçu",
            imagemResourceId = DEFAULT_INT,
            horarioFuncionamento = "8h-18h",
            telefone = DEFAULT_STRING,
            materiaisAceitos = DEFAULT_LIST,
        ),
        RecyclingLocation(
            nome = "Ecoponto",
            endereco = "Avenida Principal, 50, Campinas - São José",
            imagemResourceId = DEFAULT_INT,
            horarioFuncionamento = "9h-17h",
            telefone = DEFAULT_STRING,
            materiaisAceitos = DEFAULT_LIST,
        ),
        RecyclingLocation(
            nome = "Central Plásticos",
            endereco = "Rua Industrial, 300, Colônia - São José",
            imagemResourceId = DEFAULT_INT,
            horarioFuncionamento = "7h-19h",
            telefone = DEFAULT_STRING,
            materiaisAceitos = DEFAULT_LIST,
        ),
        RecyclingLocation(
            nome = "Metal Sucata",
            endereco = "Rodovia A, 850, Bairro Novo - Biguaçu",
            imagemResourceId = DEFAULT_INT,
            horarioFuncionamento = "8h-12h",
            telefone = DEFAULT_STRING,
            materiaisAceitos = DEFAULT_LIST,
        ),
        RecyclingLocation(
            nome = "Recicla Fácil 2",
            endereco = "Rodovia Plascido, 150, Bairro Velho - São José",
            imagemResourceId = DEFAULT_INT,
            horarioFuncionamento = "24h",
            telefone = DEFAULT_STRING,
            materiaisAceitos = DEFAULT_LIST,
        ),
    )
}