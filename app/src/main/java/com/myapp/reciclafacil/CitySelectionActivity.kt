package com.myapp.reciclafacil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class CitySelectionActivity : AppCompatActivity() {

    private val cidadesComOpcao = arrayOf("São José", "Biguaçu")
    private var cidadeSelecionada: String? = null

    private lateinit var tvCidadeSelecionada: TextView

    private val PREFS_NAME = "CidadePrefs"
    private val KEY_CIDADE = "cidade_selecionada"
    private val CITY_KEY = "CITY_NAME" // Chave usada para retornar o resultado à MainActivity
    private val IS_CHANGE_KEY = "IS_CHANGE" // Chave para saber se a MainActivity a chamou

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_selection)

        // 🎯 1. Lógica para PULAR *SE* for o PRIMEIRO lançamento do app
        // Se a cidade já foi salva E esta tela NÃO foi chamada pela MainActivity para trocar, pula.
        if (cidadeJaSelecionada() && !wasLaunchedForChange()) {
            iniciarMainActivity()
            return
        }

        val llCitySelector: LinearLayout = findViewById(R.id.ll_city_selector)
        tvCidadeSelecionada = findViewById(R.id.tv_cidade_selecionada)
        val btnConfirmar: Button = findViewById(R.id.btn_confirmar)

        // Inicializa o texto
        tvCidadeSelecionada.text = "Selecione a Cidade"

        // 2. Ação de Clique: Abre o DROPdown customizado usando PopupWindow
        llCitySelector.setOnClickListener {
            showCitySelectionDropdown(it)
        }

        // 3. Ação do Botão Confirmar
        btnConfirmar.setOnClickListener {
            if (cidadeSelecionada != null) {
                confirmarSelecao(cidadeSelecionada!!) // ⬅️ Nova função unificada
            } else {
                Toast.makeText(this, "Por favor, selecione uma cidade.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Verifica se a Activity foi chamada pela MainActivity para trocar a cidade (bandeira IS_CHANGE).
     */
    private fun wasLaunchedForChange(): Boolean {
        return intent.getBooleanExtra(IS_CHANGE_KEY, false)
    }

    /**
     * Implementa a lógica de seleção, retornando o resultado ou iniciando a MainActivity.
     */
    private fun confirmarSelecao(cidade: String) {

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_CIDADE, cidade).apply() // Salva para persistência

        if (wasLaunchedForChange()) {
            // 🎯 Se chamada para troca, retorna o resultado para a Activity chamadora
            val resultIntent = Intent().apply {
                putExtra(CITY_KEY, cidade)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Retorna
        } else {
            // 🎯 Se for o primeiro lançamento, inicia a MainActivity
            iniciarMainActivity()
        }
    }

    /**
     * Implementa um PopupWindow para controle total sobre a largura e estilo do dropdown.
     */
    private fun showCitySelectionDropdown(anchorView: View) {

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_city_list, null)

        val popupWindow = PopupWindow(
            popupView,
            anchorView.width,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        val listView = popupView.findViewById<ListView>(R.id.city_list_view)

        // Usa o construtor que aceita o ID do TextView dentro do layout customizado
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_dropdown_item,
            R.id.text_city_name,
            cidadesComOpcao
        )
        listView.adapter = adapter

        // Lógica de clique na lista
        listView.setOnItemClickListener { parent, view, position, id ->
            val cidade = cidadesComOpcao[position]
            cidadeSelecionada = cidade
            tvCidadeSelecionada.text = cidade
            popupWindow.dismiss()
        }

        // Exibe o popup ABAIXO da view âncora com offset
        val offsetDp = 6
        val density = resources.displayMetrics.density
        val offsetPx = (offsetDp * density).toInt()

        popupWindow.showAsDropDown(anchorView, 0, offsetPx)
    }

    private fun cidadeJaSelecionada(): Boolean {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.contains(KEY_CIDADE)
    }

    // Função auxiliar mantida para o fluxo de primeiro lançamento
    private fun iniciarMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}