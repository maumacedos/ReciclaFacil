package com.myapp.reciclafacil

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import kotlin.collections.ArrayList

// 🎯 NOVO: Adicione o TextView de feedback ao construtor
class LocationAdapter(
    private val initialLocations: List<RecyclingLocation>,
    private val noResultsTextView: TextView // ⬅️ Recebe o TextView da MainActivity
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private var currentLocations: MutableList<RecyclingLocation> = initialLocations.toMutableList()

    class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeEmpresa: TextView = view.findViewById(R.id.text_nome_empresa)
        val endereco: TextView = view.findViewById(R.id.text_endereco)
        val imagemFachada: ImageView = view.findViewById(R.id.image_fachada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycling_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = currentLocations[position]

        // 1. Vinculação dos dados visíveis
        holder.nomeEmpresa.text = location.nome
        holder.endereco.text = location.endereco
        holder.imagemFachada.setImageResource(location.imagemResourceId)

        // 2. Lógica de Clique no Card (COM DELAY)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)

            // Envia todos os dados detalhados via Intent
            intent.putExtra("NOME", location.nome)
            intent.putExtra("ENDERECO", location.endereco)
            intent.putExtra("IMAGEM_ID", location.imagemResourceId)
            intent.putExtra("HORARIO", location.horarioFuncionamento)
            intent.putExtra("TELEFONE", location.telefone)
            intent.putStringArrayListExtra("MATERIAIS", ArrayList(location.materiaisAceitos))

            // Micro-delay de 50ms para resolver o "salto"
            Handler(Looper.getMainLooper()).postDelayed({
                context.startActivity(intent)

                if (context is AppCompatActivity) {
                    context.overridePendingTransition(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    )
                }
            }, 50)
        }
    }

    override fun getItemCount() = currentLocations.size

    /**
     * Função pública chamada pela MainActivity para atualizar a lista exibida.
     */
    fun filterList(filteredList: List<RecyclingLocation>) {
        currentLocations.clear()
        currentLocations.addAll(filteredList)

        // 🎯 LÓGICA DE VISIBILIDADE: Controla a mensagem de "Nenhum resultado"
        if (currentLocations.isEmpty()) {
            noResultsTextView.visibility = View.VISIBLE
        } else {
            noResultsTextView.visibility = View.GONE
        }

        notifyDataSetChanged()
    }
}