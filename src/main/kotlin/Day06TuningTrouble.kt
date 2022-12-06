import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.readLines

class TuningTrouble {

    fun loadData(path: Path): String = path.readLines()[0]

    fun createWindows(dataStream: String): List<String> = dataStream.windowed(size = 4, step = 1)

    fun isStartOfPacketMarker(dataPackage: String, numberOfDigits: Int): Boolean {
        var count = 0
        for (i in 0 until numberOfDigits) {
            count += dataPackage.count { it == dataPackage[i]}
        }
        return count == numberOfDigits
    }

    fun findStartOfPacketMarker(windows: List<String>, numberOfDigit: Int): Int {
        var position = 0
        for (window in windows) {
            position++
            if (isStartOfPacketMarker(window, numberOfDigit)) return position + numberOfDigit - 1
        }
        throw IllegalStateException("Can't find start of packet marker")
    }

}

fun main() {
    val tuningTrouble = TuningTrouble()
    val dataStream = tuningTrouble.loadData(Path("src", "main", "resources", "Day06_Part1_InputData.txt"))
    val windows = tuningTrouble.createWindows(dataStream)
    val position = tuningTrouble.findStartOfPacketMarker(windows, 4)
    println("position = $position")
}