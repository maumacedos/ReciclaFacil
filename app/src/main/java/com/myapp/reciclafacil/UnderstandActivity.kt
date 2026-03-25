package com.myapp.reciclafacil

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

class UnderstandActivity : AppCompatActivity() {

    // 🎯 LISTA DE DADOS CONSOLIDADA E ÚNICA
    private val recyclableData = listOf(
        RecyclableInfo(
            nome = "Plástico",
            cor = "Vermelho", // Corrigindo para Vermelho (padrão)
            decomposicao = "400 anos",
            curiosidade = "Grande parte do plástico vem de embalagens PET e garrafas. É o mais comum.",
            imagemId = R.drawable.ic_recycling
        ),
        RecyclableInfo(
            nome = "Papel",
            cor = "Azul",
            decomposicao = "6 meses",
            curiosidade = "Reciclar uma tonelada de papel economiza 10.000 litros de água.",
            imagemId = R.drawable.ic_recycling // Use um ícone de papel real, se tiver
        ),
        RecyclableInfo(
            nome = "Vidro",
            cor = "Verde",
            decomposicao = "4000 anos",
            curiosidade = "O vidro pode ser reciclado infinitas vezes sem perder a qualidade.",
            imagemId = R.drawable.ic_recycling // Use um ícone de vidro real, se tiver
        ),
        RecyclableInfo(
            nome = "Metal",
            cor = "Amarelo",
            decomposicao = "100 anos",
            curiosidade = "A lata de alumínio é o material mais reciclado no mundo. Reciclar 1kg economiza muita energia!",
            imagemId = R.drawable.ic_recycling // Crie este ícone
        ),
        RecyclableInfo(
            nome = "Isopor",
            cor = "Branco",
            decomposicao = "Tempo indeterminado",
            curiosidade = "O isopor é 98% ar, mas demora séculos para se decompor e é difícil de reciclar em programas municipais.",
            imagemId = R.drawable.ic_recycling // Crie este ícone
        )
    )

    private lateinit var indicatorsLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_understand)

        supportActionBar?.title = "Entenda a Reciclagem"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager_recyclable)
        val adapter = RecyclableAdapter(this, recyclableData)
        viewPager.adapter = adapter

        indicatorsLayout = findViewById(R.id.layout_indicators)

        setupIndicators()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
            }
        })
    }

    /**
     * Desenha as bolinhas (ImageViews) no LinearLayout.
     */
    private fun setupIndicators() {
        indicatorsLayout.removeAllViews()

        val indicators = arrayOfNulls<ImageView>(recyclableData.size)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this!!.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.tab_indicator_default)
                )
                setLayoutParams(layoutParams)
            }
            indicatorsLayout.addView(indicators[i])
        }
        updateIndicators(0)
    }

    /**
     * Atualiza a cor das bolinhas (ativa/inativa).
     */
    private fun updateIndicators(activeIndex: Int) {
        val childCount = indicatorsLayout.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsLayout.getChildAt(i) as ImageView

            val drawableId = if (i == activeIndex)
                R.drawable.tab_indicator_selected
            else
                R.drawable.tab_indicator_default

            imageView.setImageDrawable(
                ContextCompat.getDrawable(applicationContext, drawableId)
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}