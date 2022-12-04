import java.nio.file.Path
import kotlin.io.path.readLines

class CampCleanup {

    fun createRange(data: String): IntRange {
        val borders = data.split("-")
        return IntRange(borders[0].toInt(), borders[1].toInt())
    }

    fun createRanges(data: String): Pair<IntRange, IntRange> {
        val ranges = data.split(",")
        val firstRange = createRange(ranges[0])
        val secondRange = createRange(ranges[1])
        return Pair(firstRange, secondRange)
    }

    fun loadData(path: Path): List<String> = path.readLines()

    fun createSectionAssignments(rawData: List<String>): List<Pair<IntRange, IntRange>> {
        val sectionAssignments = mutableListOf<Pair<IntRange, IntRange>>()
        for (data in rawData) {
            sectionAssignments.add(createRanges(data))
        }
        return sectionAssignments
    }

    fun containsRange(firstRange: IntRange, secondRange: IntRange): Boolean {
        if ((firstRange.first >= secondRange.first && firstRange.last <= secondRange.last)
            || secondRange.first >= firstRange.first && secondRange.last <= firstRange.last
        ) return true
        return false
    }

    fun countSectionAssignments(sectionAssignments: List<Pair<IntRange, IntRange>>): Int {
        var countSectionAssignments = 0
        for (sectionAssignment in sectionAssignments) {
            if (CampCleanup().containsRange(sectionAssignment.first, sectionAssignment.second)) {
                countSectionAssignments++
            }
        }
        return countSectionAssignments
    }

}
