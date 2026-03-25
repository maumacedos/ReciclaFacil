package com.myapp.reciclafacil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Oculta a Action Bar padrão (mantido)
        supportActionBar?.hide()

        // 1. Recebe os dados da Intent (Continua o mesmo)
        val intent = intent
        val nome = intent.getStringExtra("NOME")
        val endereco = intent.getStringExtra("ENDERECO")
        val imagemId = intent.getIntExtra("IMAGEM_ID", 0)
        val horario = intent.getStringExtra("HORARIO")
        val telefone = intent.getStringExtra("TELEFONE")
        val materiaisList = intent.getStringArrayListExtra("MATERIAIS")

        // 2. Encontra as Views de Navegação e Conteúdo
        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        val tvNome = findViewById<TextView>(R.id.detail_nome_empresa)
        val imgFachada = findViewById<ImageView>(R.id.detail_image_fachada)
        val tvEndereco = findViewById<TextView>(R.id.detail_endereco)
        val tvHorario = findViewById<TextView>(R.id.detail_horario)
        val tvTelefone = findViewById<TextView>(R.id.detail_telefone)

        // Encontra os botões de ação e o ChipGroup
        val btnCall = findViewById<ImageButton>(R.id.btn_action_call)
        val btnMap = findViewById<ImageButton>(R.id.btn_action_map)
        val chipGroupMateriais = findViewById<ChipGroup>(R.id.chip_group_materiais)

        // 3. Configura a Navegação de Retorno
        btnBack.setOnClickListener {
            finish() // ⬅️ O clique no botão de retorno chama finish()
        }

        // 4. Configura as Ações Rápidas (Ligar e Mapa)
        btnCall.setOnClickListener {
            if (!telefone.isNullOrEmpty()) {
                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$telefone")
                }
                startActivity(dialIntent)
            } else {
                Toast.makeText(this, "Telefone não disponível.", Toast.LENGTH_SHORT).show()
            }
        }

        btnMap.setOnClickListener {
            if (!endereco.isNullOrEmpty()) {
                val mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(endereco))
                val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                startActivity(mapIntent)
            }
        }

        // 5. Exibe os dados padrão
        tvNome.text = nome
        tvEndereco.text = endereco
        tvHorario.text = horario
        tvTelefone.text = telefone

        if (imagemId != 0) {
            imgFachada.setImageResource(imagemId)
        }

        // 6. Gera os Chips de Materiais
        if (!materiaisList.isNullOrEmpty()) {
            for (material in materiaisList) {
                val chip = Chip(this).apply {
                    text = material
                    isCheckable = false
                    setChipBackgroundColorResource(R.color.light_gray)
                    setTextColor(resources.getColor(R.color.black))
                }
                chipGroupMateriais.addView(chip)
            }
        }
    }

    // 🎯 ADIÇÃO CHAVE: Override do finish() para transição de retorno
    override fun finish() {
        super.finish()
        // ⬅️ Transição Inversa: A tela antiga (MainActivity) entra pela Esquerda
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}