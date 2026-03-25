package com.myapp.reciclafacil

import android.os.Parcelable // ⬅️ Importação necessária
import kotlinx.parcelize.Parcelize // ⬅️ Importação necessária

@Parcelize // 🎯 Adiciona a anotação
data class RecyclableInfo(
    val nome: String,
    val cor: String,
    val decomposicao: String,
    val curiosidade: String,
    val imagemId: Int
) : Parcelable