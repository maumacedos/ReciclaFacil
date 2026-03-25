package com.myapp.reciclafacil

// Esta classe contém a lógica de filtragem, isolada dos detalhes da Activity.
class LocationFilterHelper(private val todosLocais: List<RecyclingLocation>) {

    fun filterLocationsByCity(city: String): List<RecyclingLocation> {
        return todosLocais.filter {
            it.endereco.contains(city, ignoreCase = true)
        }
    }

    fun filterLocationsByQuery(currentSelectedCity: String, query: String): List<RecyclingLocation> {
        val queryLowerCase = query.lowercase()

        // 1. Filtra primeiro pela cidade
        val locaisDaCidade = todosLocais
            .filter { it.endereco.contains(currentSelectedCity, ignoreCase = true) }

        // 2. Filtra o resultado da cidade pela query
        return locaisDaCidade
            .filter {
                it.nome.lowercase().contains(queryLowerCase) ||
                        it.endereco.lowercase().contains(queryLowerCase)
            }
    }
}