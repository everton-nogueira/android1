package br.iesb.mobile.calculadora

/**
 *
 * Created by everton on 14/05/18.
 *
 */
sealed class Operacao
class Constant(val algorithm: Double): Operacao()
class OperacaoUnaria(val algorithm: (Double) -> Double): Operacao()
class OperacaoBinaria(val algorithm: (Double, Double) -> Double): Operacao()
class Executa(): Operacao()
class Limpa(): Operacao()

class OperacaoBinariaPendente(val Operacao: (Double, Double) -> Double, val firstOperand: Double) {
    fun executa(secondOperand: Double): Double = Operacao(firstOperand,secondOperand)
}

class CalculatorBrain {

    private var accumulator: Double = 0.0
    private val Operacaos: HashMap<String,Operacao> = hashMapOf(
            "+" to OperacaoBinaria({a,b -> a+b}),
            "-" to OperacaoBinaria({a,b -> a-b}),
            "X" to OperacaoBinaria({a,b -> a*b}),
            "/" to OperacaoBinaria({a,b -> a/b}),
            "%" to OperacaoUnaria({ a -> a/100}),
            "=" to Executa(),
            "RAIZ" to OperacaoUnaria({ a -> Math.sqrt(a)}),
            "AC" to Limpa()
    )
    private var operacaoBinariaPendente: OperacaoBinariaPendente? = null


    fun setOperand(operand: Double) {
        accumulator = operand
    }

    fun executaOperacao(OperacaoSymbol: String) {
        val Operacao = Operacaos[OperacaoSymbol]
        when(Operacao){
            is Constant -> accumulator = Operacao.algorithm
            is OperacaoUnaria -> accumulator = Operacao.algorithm(accumulator)
            is OperacaoBinaria -> operacaoBinariaPendente = OperacaoBinariaPendente((Operacao.algorithm),accumulator)
            is Executa -> performPendingOperacao()
            is Limpa -> limpa()
        }
    }

    private fun performPendingOperacao() {
        accumulator = if (operacaoBinariaPendente != null) {operacaoBinariaPendente!!.executa(accumulator)} else {accumulator}
    }


    private fun limpa() {
        accumulator = 0.0
        operacaoBinariaPendente = null
    }

    val result: Double
        get() = accumulator

}