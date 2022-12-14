class MonkeyMath(val equations: MutableList<Equation>) {

    fun insertKnownNumbersInEquations(numberValuesList: List<NumberValue>) {
        val numberValuesMap = numberValuesList.associate { it.name to it.value }
        for (equation in equations) {
            equation.number1.value = numberValuesMap[equation.number1.name] ?: equation.number1.value
            equation.number2.value = numberValuesMap[equation.number2.name] ?: equation.number2.value
        }
    }

    fun eval(equation: Equation): NumberValue? {
        if (equation.number1.value == UNRESOLVED_VALUE || equation.number2.value == UNRESOLVED_VALUE) return null
        val value = when(equation.operator) {
            '+' -> equation.number1.value + equation.number2.value
            '-' -> equation.number1.value - equation.number2.value
            '*' -> equation.number1.value * equation.number2.value
            '/' -> equation.number1.value / equation.number2.value
            else -> throw IllegalArgumentException("unknown operator [${equation.operator}")
        }
        return NumberValue(equation.name, value)
    }

    fun evalAll(): List<NumberValue> {
        return equations.mapNotNull { eval(it) }
    }

    fun solve(numberValues: List<NumberValue>, maxRuns: Int = Int.MAX_VALUE): Long {
        var newNumberValues = numberValues
        var count = 0
        while (count < maxRuns) {
            insertKnownNumbersInEquations(newNumberValues)
            newNumberValues = evalAll()
            if (isRootFound(newNumberValues)) {
                return newNumberValues.first { it.name == "root" }.value
            }
            count++
        }
        return -1
    }

    fun isRootFound(numberValues: List<NumberValue>): Boolean = numberValues.any { it.name == "root"}

    companion object {

        const val UNRESOLVED_VALUE = -1L

        fun loadData(fileName: String): List<String> = Resources.resourceAsListOfString(fileName)

        fun parseData(rawData: List<String>): Pair<List<NumberValue>, List<Equation>> {
            val first = rawData.filter { it[it.lastIndex].isDigit() }.map { parseNumberValue(it) }
            val second = rawData.filter { it[it.lastIndex].isLetter() }.map { parseEquation(it)  }
            return Pair(first, second)
        }

        fun parseNumberValue(rawData: String): NumberValue {
            val split = rawData.split(": ")
            return NumberValue(name = split[0], value = split[1].toLong())
        }

        fun parseEquation(rawData: String): Equation {
            val split = rawData.split(": ")
            return Equation(
                name = split[0],
                number1 = NumberValue(name = split[1].substring(0..3), value = UNRESOLVED_VALUE),
                number2 = NumberValue(name = split[1].substring(7..10), value = UNRESOLVED_VALUE),
                operator = split[1][5]
            )
        }
    }

}

data class NumberValue(
    val name: String,
    var value: Long,
)

data class Equation(
    val name: String,
    val number1: NumberValue,
    val number2: NumberValue,
    val operator: Char
)

fun main() {
    val rawData = MonkeyMath.loadData("Day21_InputData.txt")
    val (numberValues, equations) = MonkeyMath.parseData(rawData)
    val monkeyMath = MonkeyMath(equations.toMutableList())

    val result = monkeyMath.solve(numberValues)

    println("result = $result")
}