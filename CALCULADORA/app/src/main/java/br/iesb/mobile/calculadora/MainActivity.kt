package br.iesb.mobile.calculadora

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 *
 * Created by everton on 14/05/18.
 *
 */
class MainActivity : AppCompatActivity(){
    private var isDigitando = false
    private val temPonto: Boolean
        get() = textView.text.indexOf(".") > 0
    private var calculatorBrain = CalculatorBrain()
    private var display: Double
        get() = textView.text.toString().toDouble()
        set(newValue) {
            val valueFormatter = DecimalFormat("#.########")
            valueFormatter.roundingMode = RoundingMode.CEILING
            textView.text = valueFormatter.format(newValue)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textZero.setOnClickListener { onClickNumero(textZero.text.toString()) }
        textUm.setOnClickListener { onClickNumero(textUm.text.toString()) }
        textDois.setOnClickListener { onClickNumero(textDois.text.toString()) }
        textTres.setOnClickListener { onClickNumero(textTres.text.toString()) }
        textQuatro.setOnClickListener { onClickNumero(textQuatro.text.toString()) }
        textCinco.setOnClickListener { onClickNumero(textCinco.text.toString()) }
        textSeis.setOnClickListener { onClickNumero(textSeis.text.toString()) }
        textSete.setOnClickListener { onClickNumero(textSete.text.toString()) }
        textOito.setOnClickListener { onClickNumero(textOito.text.toString()) }
        textNove.setOnClickListener { onClickNumero(textNove.text.toString()) }
        textPonto.setOnClickListener { onClickPonto(textPonto.text.toString()) }
        textMais.setOnClickListener { onClickOperacao(textMais.text.toString()) }
        textMenos.setOnClickListener { onClickOperacao(textMenos.text.toString()) }
        textVezes.setOnClickListener { onClickOperacao(textVezes.text.toString()) }
        textDivisao.setOnClickListener { onClickOperacao(textDivisao.text.toString()) }
        textPercent.setOnClickListener { onClickOperacao(textPercent.text.toString()) }
        textIgual.setOnClickListener { onClickOperacao(textIgual.text.toString()) }
        textAC.setOnClickListener { onClickOperacao(textAC.text.toString()) }
        textRaiz.setOnClickListener { onClickOperacao(textRaiz.text.toString()) }
    }

    private fun onClickNumero(digit: String) {
        val currentValue = textView.text.toString()
        textView.text = if (isDigitando) {currentValue + digit} else {isDigitando = true; digit}
    }

    private fun onClickPonto(symbol: String) {
        if (!temPonto && isDigitando) {
            val currentValue = textView.text.toString()
            textView.text = currentValue + symbol
        }
    }

    private fun onClickOperacao(symbol: String) {
        calculatorBrain.setOperand(display)
        isDigitando = false
        calculatorBrain.executaOperacao(symbol)
        display = calculatorBrain.result
    }

}