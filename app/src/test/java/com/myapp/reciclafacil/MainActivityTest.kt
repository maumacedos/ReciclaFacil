package com.myapp.reciclafacil

import android.content.Context
import android.content.SharedPreferences
import com.myapp.reciclafacil.test.RecyclingLocationTestData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

class MainActivityTest {

    // --- Constantes
    private val PREFS_NAME = "CidadePrefs"
    private val KEY_CIDADE = "cidade_selecionada"
    private val TODOS_LOCAIS = RecyclingLocationTestData.todosLocais

    // --- Mocks
    @Mock lateinit var mockContext: Context
    @Mock lateinit var mockSharedPreferences: SharedPreferences
    @Mock lateinit var mockEditor: SharedPreferences.Editor

    // --- Helper de Lógica (Simulações de funções de MainActivity)
    // Usamos o Lazy para inicializar apenas quando for necessário, garantindo que os mocks estejam prontos
    private val locationFilterHelper by lazy {
        LocationFilterHelper(TODOS_LOCAIS)
    }

    private val prefsHelper by lazy {
        SharedPreferencesHelper(mockContext, PREFS_NAME, KEY_CIDADE)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        // Configuração de SharedPreferences (Mais Limpa)
        `when`(mockContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putString(any(), any())).thenReturn(mockEditor)
    }

    // ===============================================
    // 1. TESTES DE FILTRAGEM DE CIDADE
    // ===============================================

    @Test
    fun `filterByCity_deve_retornar_2_locais_para_Biguaçu`() {
        // ACT
        val resultados = locationFilterHelper.filterLocationsByCity("Biguaçu")

        // ASSERT
        assertEquals(2, resultados.size)
        assertTrue(resultados.all { it.endereco.contains("Biguaçu") })
    }

    @Test
    fun `filterByCity_deve_retornar_3_locais_para_Sao_Jose`() {
        val resultados = locationFilterHelper.filterLocationsByCity("São José")
        assertEquals(3, resultados.size)
    }

    @Test
    fun `filterByCity_deve_retornar_lista_vazia_para_cidade_inexistente`() {
        val resultados = locationFilterHelper.filterLocationsByCity("Palhoça")
        assertTrue(resultados.isEmpty())
    }

    // ===============================================
    // 2. TESTES DE BUSCA (QUERY)
    // ===============================================

    @Test
    fun `filterByQuery_deve_filtrar_por_nome_em_SaoJose`() {
        val resultados = locationFilterHelper.filterLocationsByQuery("São José", "Ecoponto")
        assertEquals(1, resultados.size)
        assertEquals("Ecoponto", resultados.first().nome)
    }

    @Test
    fun `filterByQuery_deve_filtrar_por_endereco_em_Biguaçu`() {
        val resultados = locationFilterHelper.filterLocationsByQuery("Biguaçu", "Rodovia")
        assertEquals(1, resultados.size)
        assertEquals("Metal Sucata", resultados.first().nome)
    }

    @Test
    fun `filterByQuery_deve_ser_case_insensitive`() {
        val resultados = locationFilterHelper.filterLocationsByQuery("São José", "plásticOs")
        assertEquals(1, resultados.size)
        assertEquals("Central Plásticos", resultados.first().nome)
    }

    // ===============================================
    // 3. TESTES DE SHARED PREFERENCES
    // ===============================================

    @Test
    fun `saveCity_deve_chamar_editor_putString_e_apply`() {
        val novaCidade = "Florianópolis"

        // ACT
        prefsHelper.saveSelectedCity(novaCidade)

        // ASSERT
        verify(mockEditor).putString(KEY_CIDADE, novaCidade)
        verify(mockEditor).apply()
    }

    @Test
    fun `buscarCity_deve_retornar_cidade_salva`() {
        val cidadeEsperada = "Florianópolis"

        // Arrange: Configura o retorno antes da chamada
        `when`(mockSharedPreferences.getString(KEY_CIDADE, "")).thenReturn(cidadeEsperada)

        // ACT
        val cidadeRetornada = prefsHelper.buscarCidadeSelecionada()

        // ASSERT
        assertEquals(cidadeEsperada, cidadeRetornada)
        verify(mockSharedPreferences).getString(KEY_CIDADE, "")
    }

    @Test
    fun `buscarCity_deve_retornar_vazio_quando_nao_houver_cidade_salva`() {
        val cidadeEsperada = ""

        // Arrange: Configura para retornar vazio/padrão
        `when`(mockSharedPreferences.getString(KEY_CIDADE, "")).thenReturn(cidadeEsperada)

        // ACT
        val cidadeRetornada = prefsHelper.buscarCidadeSelecionada()

        // ASSERT
        assertEquals(cidadeEsperada, cidadeRetornada)
    }
}