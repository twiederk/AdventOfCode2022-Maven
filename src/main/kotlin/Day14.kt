import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.max

typealias Cave = Array<CharArray>
typealias Position = Pair<Int, Int>

class RegolithReservoir {

    companion object {
        val ExitPosition = Position(-1, -1)
    }

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

    fun maxX(lines: List<Line>): Int {
        val maxStartX = lines.maxBy { it.startX }.startX
        val maxEndX = lines.maxBy { it.endX }.endX
        return max(maxStartX, maxEndX)
    }

    fun maxY(lines: List<Line>): Int {
        val maxStartY = lines.maxBy { it.startY }.startY
        val maxEndY = lines.maxBy { it.endY }.endY
        return max(maxStartY, maxEndY)
    }

    fun createCave(lines: List<Line>): Cave {

        val maxX = maxX(lines) * 2
        val maxY = maxY(lines) + 1

        val cave = Array(maxY) { CharArray(maxX) }
        emptyCave(maxY, maxX, cave)
        drawRocks(lines, cave)

        return cave
    }

    private fun drawRocks(lines: List<Line>, cave: Array<CharArray>) {
        for (line in lines) {
            if (line.startY == line.endY) {
                drawHorizontalLine(line, cave)
            } else {
                drawVerticalLine(line, cave)
            }
        }
    }

    private fun emptyCave(maxY: Int, maxX: Int, cave: Array<CharArray>) {
        for (y in 0 until maxY) {
            for (x in 0 until maxX) {
                cave[y][x] = '.'
            }
        }
    }

    private fun drawVerticalLine(line: Line, cave: Array<CharArray>) {
        val rangeY = if (line.startY < line.endY) line.startY..line.endY else line.endY..line.startY
        for (y in rangeY) {
            cave[y][line.startX] = '#'
        }
    }

    private fun drawHorizontalLine(line: Line, cave: Array<CharArray>) {
        val rangeX = if (line.startX < line.endX) line.startX..line.endX else line.endX..line.startX
        for (x in rangeX) {
            cave[line.startY][x] = '#'
        }
    }

    fun renderCave(
        cave: Array<CharArray>,
        startX: Int = 0,
        startY: Int = 0,
        endX: Int = cave[0].size,
        endY: Int = cave.size
    ): String {
        val display = StringBuffer()
        for (y in startY until endY) {
            for (x in startX until endX) {
                display.append(cave[y][x])
            }
            display.append('\n')
        }
        return display.toString()
    }

    fun fallingDown(cave: Array<CharArray>, startPosition: Pair<Int, Int>): Position {
        // out of bounds
        if (startPosition.second + 1 >= cave.size) {
            return ExitPosition
        }

        // down one step
        if (cave[startPosition.second + 1][startPosition.first] == '.') {
            return Position(first = startPosition.first, second = startPosition.second + 1)
        }
        // one step down and to the left
        if (cave[startPosition.second + 1][startPosition.first - 1] == '.') {
            return Position(first = startPosition.first - 1, second = startPosition.second + 1)
        }
        // one step down and to the right
        if (cave[startPosition.second + 1][startPosition.first + 1] == '.') {
            return Position(first = startPosition.first + 1, second = startPosition.second + 1)
        }
        return startPosition
    }

    fun fallingToRest(cave: Array<CharArray>, startPosition: Pair<Int, Int>): Position {
        var currentPosition = startPosition
        for (y in cave.indices) {
            val nextPosition = fallingDown(cave, currentPosition)
            if (nextPosition == currentPosition) {
                cave[currentPosition.second][currentPosition.first] = 'o'
                return currentPosition
            }
            currentPosition = nextPosition
        }
        return ExitPosition
    }

    fun drizzle(cave: Array<CharArray>, startPosition: Pair<Int, Int>): Int {
        var count = 0
        while (true) {
//            println(renderCave(cave, startX = 494))
            val restPosition = fallingToRest(cave, startPosition)
            if (restPosition == ExitPosition || restPosition == startPosition) {
                return count
            }
            count++
        }
    }

    fun createCaveWithFloor(lines: List<Line>): Cave {
        var cave = createCave(lines)
        cave += CharArray(cave[0].size)
        cave += CharArray(cave[0].size)
        for (x in cave[0].indices) {
            cave[cave.lastIndex - 1][x] = '.'
            cave[cave.lastIndex][x] = '#'
        }
        return cave
    }

    fun drizzleWithFloor(cave: Array<CharArray>, startPosition: Pair<Int, Int>): Int = drizzle(cave, startPosition) + 1


}

data class Line(val startX: Int, val startY: Int, val endX: Int, val endY: Int)

fun main() {
    val regolithReservoir = RegolithReservoir()
    val rawData = regolithReservoir.loadData(Path("src", "main", "resources", "Day14_InputData.txt"))
    val lines = regolithReservoir.createLines(rawData)
    val startPosition = Position(500, 0)

    part1(regolithReservoir, lines, startPosition)
    part2(regolithReservoir, lines, startPosition)

}

private fun part1(
    regolithReservoir: RegolithReservoir,
    lines: List<Line>,
    startPosition: Position
) {
    val cave = regolithReservoir.createCave(lines)
    val unitOfSandNoFloor = regolithReservoir.drizzle(cave, startPosition)

//    println(regolithReservoir.renderCave(cave))
    println("unitOfSandNoFloor = $unitOfSandNoFloor")
}

private fun part2(
    regolithReservoir: RegolithReservoir,
    lines: List<Line>,
    startPosition: Position
) {
    val caveWithFloor = regolithReservoir.createCaveWithFloor(lines)
    val unitOfSandWithFloor = regolithReservoir.drizzleWithFloor(caveWithFloor, startPosition)
//    println(regolithReservoir.renderCave(caveWithFloor))
    println("unitOfSandWithFloor = $unitOfSandWithFloor")
}
