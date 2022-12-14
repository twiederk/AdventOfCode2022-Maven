import java.nio.file.Path
import kotlin.io.path.readLines


class RegolithReservoir {

    fun loadData(path: Path) = path.readLines()

    fun createLines(rawData: List<String>): List<Line> {
        val lines = mutableListOf<Line>()
        for (raw in rawData) {
            val split = raw.split(" -> ")
            val windows = split.windowed(2, 1)
            for (window in windows) {
                val (startX, startY) = window[0].split(",")
                val (endX, endY) = window[1].split(",")
                lines.add(Line(startX.toInt(), startY.toInt(), endX.toInt(), endY.toInt()))
            }

        }
        return lines
    }

}

data class Line(val startX: Int, val startY: Int, val endX: Int, val endY: Int)