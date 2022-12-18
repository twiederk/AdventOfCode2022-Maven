class BoilingBoulders {

    fun loadData(fileName: String): List<Cube> {
        val lines = Resources.resourceAsListOfString(fileName)
        return lines.map { it.split(',') }.map { Cube(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
    }

    fun solve(cubes: List<Cube>): Int {
        var countAdjacent = 0

        for (cube in cubes) {
            for (other in cubes) {
                if (cube == other) continue
                if (cube.isAdjacent(other)) countAdjacent++
            }
        }

        return cubes.size * 6 - countAdjacent
    }


}

data class Point3D(
    val x: Int,
    val y: Int,
    val z: Int,
)

data class Cube(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    val sides = mutableSetOf<Side>()
    val cornerPoints = mutableListOf<Point3D>()

    init {
        createCornerPoints()
        createSides()
    }

    fun createCornerPoints() {
        cornerPoints.clear()
        cornerPoints.add(Point3D(x - 1, y - 1, z - 1)) // A
        cornerPoints.add(Point3D(x - 1, y, z - 1)) // B
        cornerPoints.add(Point3D(x, y, z - 1)) // C
        cornerPoints.add(Point3D(x, y - 1, z - 1)) // D
        cornerPoints.add(Point3D(x - 1, y - 1, z)) // E
        cornerPoints.add(Point3D(x - 1, y, z)) // F
        cornerPoints.add(Point3D(x, y, z)) // G
        cornerPoints.add(Point3D(x, y - 1, z)) // H

    }

    fun createSides() {
        sides.clear()
        sides.add(Side(setOf(cornerPoints[0], cornerPoints[1], cornerPoints[2], cornerPoints[3]))) // back
        sides.add(Side(setOf(cornerPoints[4], cornerPoints[5], cornerPoints[6], cornerPoints[7]))) // front
        sides.add(Side(setOf(cornerPoints[1], cornerPoints[2], cornerPoints[5], cornerPoints[6]))) // top
        sides.add(Side(setOf(cornerPoints[0], cornerPoints[3], cornerPoints[4], cornerPoints[7]))) // bottom
        sides.add(Side(setOf(cornerPoints[0], cornerPoints[1], cornerPoints[4], cornerPoints[5]))) // left
        sides.add(Side(setOf(cornerPoints[2], cornerPoints[3], cornerPoints[6], cornerPoints[7]))) // right

    }

    fun isAdjacent(other: Cube) : Boolean {
        return (sides intersect other.sides).size == 1
    }
}

data class Side(val points: Set<Point3D>)
