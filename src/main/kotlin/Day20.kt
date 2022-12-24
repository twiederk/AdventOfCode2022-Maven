class Day20(input: List<Int>) {

    val originalList: List<Original> = input.map { Original(value = it, data = Data(it)) }
    var mixList: MutableList<Data> = mutableListOf()

    init {
        originalList.forEach { mixList.add(it.data) }
    }

    fun mix(original: Original) {
        val oldIndex = mixList.indexOf(original.data)
        val toBeMoved = mixList.removeAt(oldIndex)
        val newIndex = getNewIndex(oldIndex, original.value, originalList.size)
        mixList.add(newIndex, toBeMoved)
    }

    fun getNewIndex(oldIndex: Int, step: Int, size: Int): Int {
        var newIndex: Int
        if (step < 0) {
            newIndex = (oldIndex + step).mod(size)
            return if (oldIndex > newIndex) newIndex else newIndex - 1
        }
        newIndex = (oldIndex + step).mod(size)
        return if (oldIndex > newIndex) newIndex - 1 else newIndex

    }

    fun mixAll(): Int {
        originalList.forEach { mix(it) }
        return calculateGroveCoordinates()
    }

    fun calculateGroveCoordinates(): Int {
        val zero = mixList.indexOfFirst { it.value == 0 }
        return listOf(1000, 2000, 3000).sumOf { mixList[(zero + it) % mixList.size].value }
    }

}

class Data(val value: Int) {

    override fun toString(): String = value.toString()
}

data class Original(val value: Int, val data: Data)

fun main() {
    val input = Resources.resourceAsListOfInt("Day20_InputData.txt")
    val day20 = Day20(input)

    val groveCoordinates = day20.mixAll()

    println("groveCoordinates = $groveCoordinates")
}