package com.myapp.reciclafacil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val PREFS_NAME = "CidadePrefs"
    private val KEY_CIDADE = "cidade_selecionada"
    private val CITY_KEY = "CITY_NAME"
    private val IS_CHANGE_KEY = "IS_CHANGE"

    // VARIÁVEIS GLOBAIS
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var currentSelectedCity: String
    private lateinit var drawerLayout: DrawerLayout

    // Dados de teste (todos os locais)
    private val todosLocais = listOf(
        RecyclingLocation(
            nome = "Recicla Fácil",
            endereco = "Rua do Teste, 100, Centro - Biguaçu",
            imagemResourceId = R.drawable.local4,
            horarioFuncionamento = "Segunda a Sexta: 8h às 18h",
            telefone = "(48) 3243-XXXX",
            materiaisAceitos = listOf("Plástico", "Metal", "Papel")
        ),
        RecyclingLocation(
            nome = "Ecoponto",
            endereco = "Avenida Principal, 50, Campinas - São José",
            imagemResourceId = R.drawable.local1,
            horarioFuncionamento = "Segunda a Sábado: 9h às 17h",
            telefone = "(48) 3258-YYYY",
            materiaisAceitos = listOf("Pilhas", "Baterias", "Eletrônicos", "Vidro")
        ),
        RecyclingLocation(
            nome = "Central Plásticos",
            endereco = "Rua Industrial, 300, Colônia - São José",
            imagemResourceId = R.drawable.local2,
            horarioFuncionamento = "Segunda a Sexta: 7h às 19h",
            telefone = "(48) 98765-1234",
            materiaisAceitos = listOf("Plástico", "Vidro", "Isopor")
        ),
        RecyclingLocation(
            nome = "Metal Sucata",
            endereco = "Rodovia A, 850, Bairro Novo - Biguaçu",
            imagemResourceId = R.drawable.local5,
            horarioFuncionamento = "Apenas Sábados: 8h às 12h",
            telefone = "(48) 3243-9876",
            materiaisAceitos = listOf("Metal", "Ferro", "Cobre", "Eletrônicos")
        ),
        RecyclingLocation(
            nome = "Recicla Fácil 2",
            endereco = "Rodovia Plascido, 150, Bairro Velho - São José",
            imagemResourceId = R.drawable.local3,
            horarioFuncionamento = "Todos os dias: 24h",
            telefone = "(48) 99887-6655",
            materiaisAceitos = listOf("Papel", "Vidro", "Móveis")
        ),
    )

    // ===============================================
    // IMPLEMENTAÇÃO DO LAUNCHER (Recebe resultado)
    // ===============================================
    private val citySelectionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newCity = result.data?.getStringExtra(CITY_KEY)

            if (newCity != null && newCity != currentSelectedCity) {
                saveSelectedCity(newCity)
                currentSelectedCity = newCity
                loadLocationsForCity(newCity)
                supportActionBar?.title = "Locais em $newCity"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Encontra os componentes do Drawer e Views Principais
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_locais)
        val searchView: SearchView = findViewById(R.id.search_bar_locais)
        val btnChangeCity = findViewById<ImageButton>(R.id.btn_change_city)
        val noResultsTextView: TextView = findViewById(R.id.text_no_results)

        // 2. Carrega a cidade inicial e configura o Adapter
        currentSelectedCity = buscarCidadeSelecionada()
        val locaisIniciais = filterLocationsByCity(currentSelectedCity)
        locationAdapter = LocationAdapter(locaisIniciais, noResultsTextView)
        recyclerView.adapter = locationAdapter

        // 3. CONEXÃO DO BOTÃO (HAMBÚRGUER) PARA ABRIR O MENU
        btnChangeCity.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // 4. 🎯 LÓGICA DE CONEXÃO DOS ITENS DO MENU LATERAL
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_change_city -> {
                    startCitySelection()
                }
                R.id.nav_developers -> {
                    val developersIntent = Intent(this, DevelopersActivity::class.java)
                    startActivity(developersIntent)
                }
                R.id.nav_understand -> {
                    val understandIntent = Intent(this, UnderstandActivity::class.java)
                    startActivity(understandIntent)
                }
                R.id.nav_logout -> {
                    // 🚪 EFETUA O LOGOUT: Instancia o repositório e limpa a sessão do dispositivo
                    val userRepository = UserRepository(this)
                    userRepository.logoutUser()

                    // Prepara a navegação de retorno à tela de Login
                    val loginIntent = Intent(this, LoginActivity::class.java)

                    // Limpa todo o histórico de telas por segurança
                    loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(loginIntent)
                    finish()
                }
            }
            // Fecha o menu lateral após o clique
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // 5. Configuração do listener de pesquisa
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterLocationsByQuery(newText.orEmpty())
                return true
            }
        })

        // 6. Configuração final
        locationAdapter.filterList(locaisIniciais)
        supportActionBar?.title = "Locais em $currentSelectedCity"

        // 7. 🚪 Gerenciamento moderno do botão Voltar (Para fechar o menu com gestos se aberto)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    // ===============================================
    // FUNÇÕES DE SUPORTE
    // ===============================================

    private fun buscarCidadeSelecionada(): String {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_CIDADE, "") ?: ""
    }

    private fun saveSelectedCity(city: String) {
        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit {
                putString(KEY_CIDADE, city)
            }
    }

    private fun startCitySelection() {
        val intent = Intent(this, CitySelectionActivity::class.java).apply {
            putExtra(IS_CHANGE_KEY, true)
        }
        citySelectionLauncher.launch(intent)
    }

    private fun filterLocationsByCity(city: String): List<RecyclingLocation> {
        return todosLocais.filter {
            it.endereco.contains(city, ignoreCase = true)
        }
    }

    private fun loadLocationsForCity(city: String) {
        val locaisDaNovaCidade = filterLocationsByCity(city)
        locationAdapter.filterList(locaisDaNovaCidade)
        findViewById<SearchView>(R.id.search_bar_locais).setQuery("", false)
    }

    private fun filterLocationsByQuery(query: String) {
        val queryLowerCase = query.lowercase()

        val filteredList = todosLocais
            .filter { it.endereco.contains(currentSelectedCity, ignoreCase = true) }
            .filter {
                it.nome.lowercase().contains(queryLowerCase) ||
                        it.endereco.lowercase().contains(queryLowerCase)
            }

        locationAdapter.filterList(filteredList)
    }
}