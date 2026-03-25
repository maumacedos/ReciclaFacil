package com.myapp.reciclafacil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat // ⬅️ Necessário para ler cores do recurso
import android.content.Context
import android.graphics.Color // ⬅️ Necessário para lidar com cores (opcional, mas bom)

class RecyclableFragment : Fragment() {

    companion object {
        private const val ARG_INFO = "info_data"

        // Assume-se que RecyclableInfo agora implementa Parcelable
        fun newInstance(info: RecyclableInfo) =
            RecyclableFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_INFO, info)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragment
        return inflater.inflate(R.layout.fragment_recyclable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Receber os dados
        // Use requireArguments() para evitar NPE
        val info: RecyclableInfo? = arguments?.getParcelable(ARG_INFO)

        if (info != null) {

            // 2. Mapear a cor do sistema
            val colorResId = getColorResource(info.cor)
            // Obtém o valor real da cor (Int)
            val colorValue = ContextCompat.getColor(requireContext(), colorResId)

            // 3. Encontrar as Views
            val tvNome = view.findViewById<TextView>(R.id.text_nome)
            val tvDecomp = view.findViewById<TextView>(R.id.text_decomp_value)
            val tvCuriosity = view.findViewById<TextView>(R.id.text_curiosity_value)
            val img = view.findViewById<ImageView>(R.id.image_recyclable)

            // 4. Exibir os dados
            tvNome.text = "${info.nome} (${info.cor})"
            tvDecomp.text = info.decomposicao
            tvCuriosity.text = info.curiosidade
            img.setImageResource(info.imagemId)

            // 🎯 AÇÃO: Aplicar a Cor Dinâmica
            tvNome.setTextColor(colorValue) // Colore o nome do material
            img.setColorFilter(colorValue) // Colore o ícone (Tinting)

            // 🎯 Opcional: Colorir o rótulo de tempo de decomposição para destaque
            //val tvLabelDecomp = view.findViewById<TextView>(R.id.label_decomp)
            //tvLabelDecomp.setTextColor(colorValue)
        }
    }

    /**
     * Mapeia o nome da cor (String) para o ID do recurso de cor (R.color.sua_cor).
     * @param colorName Nome da cor (ex: "Azul").
     * @return R.color ID.
     */
    private fun getColorResource(colorName: String): Int {
        return when (colorName.lowercase()) {
            "azul" -> R.color.blue
            "vermelho" -> R.color.red
            "amarelo" -> R.color.yellow
            "verde" -> R.color.green
            "branco" -> R.color.black // Usaremos preto para o branco para garantir visibilidade
            else -> R.color.black // Padrão
        }
    }
}