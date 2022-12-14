import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.io.path.Path


class HillClimbingAlgorithmTest {

    private val hillClimbingAlgorithm = HillClimbingAlgorithm()
    private val grid = hillClimbingAlgorithm.loadData(Path("src", "test", "resources", "Day12_TestData.txt"))

    @Test
    fun loadData() {
        // act
        val grid = hillClimbingAlgorithm.loadData(Path("src", "test", "resources", "Day12_TestData.txt"))

        // assert
        assertThat(grid[0]).hasSize(8)
        assertThat(grid).hasSize(5)
    }

    @Test
    fun nodeComparator_firstNodeLessWeight() {
        // arrange
        val node1 = Node().apply { f = 1 }
        val node2 = Node().apply { f = 2 }

        // act
        val result = node1.compareTo(node2)

        // assert
        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun nodeComparator_firstNodeMoreWeight() {
        // arrange
        val node1 = Node().apply { f = 2 }
        val node2 = Node().apply { f = 1 }

        // act
        val result = node1.compareTo(node2)

        // assert
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun nodeComparator_sameWeight() {
        // arrange
        val node1 = Node().apply { f = 2 }
        val node2 = Node().apply { f = 2 }

        // act
        val result = node1.compareTo(node2)

        // assert
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun findStartNode() {

        // act
        val start = hillClimbingAlgorithm.findStartNode(grid)

        // assert
        assertThat(start).isEqualTo(Node(0, 0))
    }

    @Test
    fun findEndNode() {

        // act
        val start = hillClimbingAlgorithm.findEndNode(grid)

        // assert
        assertThat(start).isEqualTo(Node(2, 5))
    }

    @Test
    fun getNeighbors_00_twoNeighbors() {

        // act
        val neighbors = hillClimbingAlgorithm.getNeighbors(grid, Node(0, 0))

        // assert
        assertThat(neighbors).containsExactly(
            Node(0, 1),
            Node(1, 0)
        )
    }

    @Test
    fun getNeighbors_10_threeNeighbors() {

        // act
        val neighbors = hillClimbingAlgorithm.getNeighbors(grid, Node(1, 0))

        // assert
        assertThat(neighbors).containsExactly(
            Node(0, 0),
            Node(1, 1),
            Node(2, 0),
        )
    }

    @Test
    fun getNeighbors_11_fourNeighbors() {

        // act
        val neighbors = hillClimbingAlgorithm.getNeighbors(grid, Node(1, 1))

        // assert
        assertThat(neighbors).containsExactly(
            Node(0, 1),
            Node(1, 2),
            Node(2, 1),
            Node(1, 0),
        )
    }

    @Test
    fun getNeighbors_01_fourNeighbors() {

        // act
        val neighbors = hillClimbingAlgorithm.getNeighbors(grid, Node(0, 1))

        // assert
        assertThat(neighbors).containsExactly(
            Node(0, 2),
            Node(1, 1),
            Node(0, 0),
        )
    }

    @Test
    fun getNeighbors_02_twoNeighbors() {

        // act
        val neighbors = hillClimbingAlgorithm.getNeighbors(grid, Node(0, 2))

        // assert
        assertThat(neighbors).containsExactly(
            Node(1, 2),
            Node(0, 1),
        )
    }

    @Test
    fun getNeighbors_44_twoNeighbors() {

        // act
        val neighbors = hillClimbingAlgorithm.getNeighbors(grid, Node(4, 4))

        // assert
        assertThat(neighbors).containsExactly(
            Node(4, 5),
            Node(4, 3),
        )
    }

    @Test
    fun isClimbable_ab_true() {

        // act
        val climbable = hillClimbingAlgorithm.isClimbable(grid, Node(0, 1), Node(0, 2))

        // assert
        assertThat(climbable).isTrue
    }

    @Test
    fun isClimbable_bq_false() {

        // act
        val climbable = hillClimbingAlgorithm.isClimbable(grid, Node(0, 2), Node(0, 3))

        // assert
        assertThat(climbable).isFalse
    }

    @Test
    fun isClimbable_fg_true() {

        // act
        val climbable = hillClimbingAlgorithm.isClimbable(grid, Node(4, 4), Node(4, 5))

        // assert
        assertThat(climbable).isTrue
    }

    @Test
    fun distance_00_11_is2() {

        // act
        val distance = hillClimbingAlgorithm.distance(Node(0, 0), Node(1, 1))

        // assert
        assertThat(distance).isEqualTo(2)

    }

    @Test
    fun expandNode_00() {

        // act
        hillClimbingAlgorithm.expandNode(grid, Node(0, 0), Node(2, 5))

        // assert
        assertThat(hillClimbingAlgorithm.openList).hasSize(2)
    }

    @Test
    fun expandNode_44() {

        // act
        hillClimbingAlgorithm.expandNode(grid, Node(4, 4), Node(2, 5))

        // assert
        assertThat(hillClimbingAlgorithm.openList).hasSize(2)
    }

    @Test
    fun aStar() {

        // act
        val result = hillClimbingAlgorithm.aStar(grid, Node(0, 0))

        // assert
        assertThat(result.size).isEqualTo(32)
    }

    @Test
    fun aStar_step1() {

        // act
        hillClimbingAlgorithm.aStar(grid, Node(0, 0), 1)

        // assert
        // expandNode(0, 0)
        val openList = hillClimbingAlgorithm.openList
        assertThat(openList).hasSize(2)
        NodeAssert(openList.elementAt(0)).hasCoords(0, 1).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(openList.elementAt(1)).hasCoords(1, 0).hasG(1).hasF(7).hasParent(Node(0, 0))

        val closedList = hillClimbingAlgorithm.closedList
        assertThat(closedList).hasSize(1)
        NodeAssert(closedList.elementAt(0)).hasCoords(0, 0).hasG(0).hasF(0)
    }

    @Test
    fun aStar_step2() {

        // act
        hillClimbingAlgorithm.aStar(grid, Node(0, 0), 2)

        // assert
        // expand Node(0, 1)
        val openList = hillClimbingAlgorithm.openList
//        openList = [Node(row=1, col=0), Node(row=0, col=2), Node(row=1, col=1)]
        assertThat(openList).hasSize(3)
        NodeAssert(openList.elementAt(0)).hasCoords(1, 0).hasG(1).hasF(7)
        NodeAssert(openList.elementAt(1)).hasCoords(0, 2).hasG(2).hasF(7).hasParent(Node(0, 1))
        NodeAssert(openList.elementAt(2)).hasCoords(1, 1).hasG(2).hasF(7).hasParent(Node(0, 1))

        val closedList = hillClimbingAlgorithm.closedList
        assertThat(closedList).hasSize(2)
        NodeAssert(closedList.elementAt(0)).hasCoords(0, 0).hasG(0).hasF(0)
        NodeAssert(closedList.elementAt(1)).hasCoords(0, 1).hasG(1).hasF(7).hasParent(Node(0, 0))
    }

    @Test
    fun aStar_step3() {

        // act
        hillClimbingAlgorithm.aStar(grid, Node(0, 0), 3)

        // assert
        // expand Node(1, 0)
        val openList = hillClimbingAlgorithm.openList
//        openList = [Node(row=1, col=1), Node(row=0, col=2), Node(row=2, col=0)]
        assertThat(openList).hasSize(3)
        NodeAssert(openList.elementAt(0)).hasCoords(1, 1).hasG(2).hasF(7)
        NodeAssert(openList.elementAt(1)).hasCoords(0, 2).hasG(2).hasF(7)
        NodeAssert(openList.elementAt(2)).hasCoords(2, 0).hasG(2).hasF(7).hasParent(Node(1, 0))

        val closedList = hillClimbingAlgorithm.closedList
        assertThat(closedList).hasSize(3)
        NodeAssert(closedList.elementAt(0)).hasCoords(0, 0).hasG(0).hasF(0)
        NodeAssert(closedList.elementAt(1)).hasCoords(0, 1).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(2)).hasCoords(1, 0).hasG(1).hasF(7).hasParent(Node(0, 0))
    }

    @Test
    fun aStar_step4() {

        // act
        hillClimbingAlgorithm.aStar(grid, Node(0, 0), 4)

        // assert
        // expand Node(1, 1)
        val openList = hillClimbingAlgorithm.openList
//        openList = [Node(row=2, col=0), Node(row=0, col=2), Node(row=1, col=2), Node(row=2, col=1)]
        assertThat(openList).hasSize(4)
        NodeAssert(openList.elementAt(0)).hasCoords(2, 0).hasG(2).hasF(7)
        NodeAssert(openList.elementAt(1)).hasCoords(0, 2).hasG(2).hasF(7)
        NodeAssert(openList.elementAt(2)).hasCoords(1, 2).hasG(3).hasF(7).hasParent(Node(1, 1))
        NodeAssert(openList.elementAt(3)).hasCoords(2, 1).hasG(3).hasF(7).hasParent(Node(1, 1))

        val closedList = hillClimbingAlgorithm.closedList
        assertThat(closedList).hasSize(4)
        NodeAssert(closedList.elementAt(0)).hasCoords(0, 0).hasG(0).hasF(0)
        NodeAssert(closedList.elementAt(1)).hasCoords(0, 1).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(2)).hasCoords(1, 0).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(3)).hasCoords(1, 1).hasG(2).hasF(7).hasParent(Node(0, 1))
    }

    @Test
    fun aStar_step5() {

        // act
        hillClimbingAlgorithm.aStar(grid, Node(0, 0), 5)

        // assert
        // expand Node(2, 0)
        val openList = hillClimbingAlgorithm.openList
//        openList = [Node(row=2, col=1), Node(row=0, col=2), Node(row=1, col=2), Node(row=3, col=0)]
        assertThat(openList).hasSize(4)
        NodeAssert(openList.elementAt(0)).hasCoords(2, 1).hasG(3).hasF(7)
        NodeAssert(openList.elementAt(1)).hasCoords(0, 2).hasG(2).hasF(7)
        NodeAssert(openList.elementAt(2)).hasCoords(1, 2).hasG(3).hasF(7)
        NodeAssert(openList.elementAt(3)).hasCoords(3, 0).hasG(3).hasF(9)

        val closedList = hillClimbingAlgorithm.closedList
        assertThat(closedList).hasSize(5)
        NodeAssert(closedList.elementAt(0)).hasCoords(0, 0).hasG(0).hasF(0)
        NodeAssert(closedList.elementAt(1)).hasCoords(0, 1).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(2)).hasCoords(1, 0).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(3)).hasCoords(1, 1).hasG(2).hasF(7).hasParent(Node(0, 1))
        NodeAssert(closedList.elementAt(4)).hasCoords(2, 0).hasG(2).hasF(7).hasParent(Node(1, 0))
    }

    @Test
    fun aStar_step6() {

        // act
        hillClimbingAlgorithm.aStar(grid, Node(0, 0), 6)

        // assert
        // expand Node(2, 1)
        val openList = hillClimbingAlgorithm.openList
//        openList = [Node(row=0, col=2), Node(row=2, col=2), Node(row=1, col=2), Node(row=3, col=0), Node(row=3, col=1)]
        assertThat(openList).hasSize(5)
        NodeAssert(openList.elementAt(0)).hasCoords(0, 2).hasG(2).hasF(7)
        NodeAssert(openList.elementAt(1)).hasCoords(2, 2).hasG(4).hasF(7)
        NodeAssert(openList.elementAt(2)).hasCoords(1, 2).hasG(3).hasF(7)
        NodeAssert(openList.elementAt(3)).hasCoords(3, 0).hasG(3).hasF(9)
        NodeAssert(openList.elementAt(4)).hasCoords(3, 1).hasG(4).hasF(9)

        val closedList = hillClimbingAlgorithm.closedList
        assertThat(closedList).hasSize(6)
        NodeAssert(closedList.elementAt(0)).hasCoords(0, 0).hasG(0).hasF(0)
        NodeAssert(closedList.elementAt(1)).hasCoords(0, 1).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(2)).hasCoords(1, 0).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(3)).hasCoords(1, 1).hasG(2).hasF(7).hasParent(Node(0, 1))
        NodeAssert(closedList.elementAt(4)).hasCoords(2, 0).hasG(2).hasF(7).hasParent(Node(1, 0))
        NodeAssert(closedList.elementAt(5)).hasCoords(2, 1).hasG(3).hasF(7).hasParent(Node(1, 1))
    }

    @Test
    fun aStar_step7() {

        // act
        hillClimbingAlgorithm.aStar(grid, Node(0, 0), 7)

        // assert
        // expand Node(0, 2)
        val openList = hillClimbingAlgorithm.openList
//        openList = [Node(row=2, col=2), Node(row=3, col=1), Node(row=1, col=2), Node(row=3, col=0)]
        assertThat(openList).hasSize(4)
        NodeAssert(openList.elementAt(0)).hasCoords(2, 2).hasG(4).hasF(7)
        NodeAssert(openList.elementAt(1)).hasCoords(3, 1).hasG(4).hasF(9)
        NodeAssert(openList.elementAt(2)).hasCoords(1, 2).hasG(3).hasF(7)
        NodeAssert(openList.elementAt(3)).hasCoords(3, 0).hasG(3).hasF(9)

        val closedList = hillClimbingAlgorithm.closedList
        assertThat(closedList).hasSize(7)
        NodeAssert(closedList.elementAt(0)).hasCoords(0, 0).hasG(0).hasF(0)
        NodeAssert(closedList.elementAt(1)).hasCoords(0, 1).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(2)).hasCoords(1, 0).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(3)).hasCoords(1, 1).hasG(2).hasF(7).hasParent(Node(0, 1))
        NodeAssert(closedList.elementAt(4)).hasCoords(2, 0).hasG(2).hasF(7).hasParent(Node(1, 0))
        NodeAssert(closedList.elementAt(5)).hasCoords(2, 1).hasG(3).hasF(7).hasParent(Node(1, 1))
        NodeAssert(closedList.elementAt(6)).hasCoords(0, 2).hasG(2).hasF(7).hasParent(Node(0, 1))
    }

    @Test
    fun aStar_step8() {

        // act
        hillClimbingAlgorithm.aStar(grid, Node(0, 0), 8)

        // assert
        // expand Node(2, 2)
        val openList = hillClimbingAlgorithm.openList
//        openList = [Node(row=1, col=2), Node(row=3, col=1), Node(row=3, col=0), Node(row=3, col=2)]
        assertThat(openList).hasSize(4)
        NodeAssert(openList.elementAt(0)).hasCoords(1, 2).hasG(3).hasF(7)
        NodeAssert(openList.elementAt(1)).hasCoords(3, 1).hasG(4).hasF(9)
        NodeAssert(openList.elementAt(2)).hasCoords(3, 0).hasG(3).hasF(9)
        NodeAssert(openList.elementAt(3)).hasCoords(3, 2).hasG(5).hasF(9).hasParent(Node(2, 2))

        val closedList = hillClimbingAlgorithm.closedList
        assertThat(closedList).hasSize(8)
        NodeAssert(closedList.elementAt(0)).hasCoords(0, 0).hasG(0).hasF(0)
        NodeAssert(closedList.elementAt(1)).hasCoords(0, 1).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(2)).hasCoords(1, 0).hasG(1).hasF(7).hasParent(Node(0, 0))
        NodeAssert(closedList.elementAt(3)).hasCoords(1, 1).hasG(2).hasF(7).hasParent(Node(0, 1))
        NodeAssert(closedList.elementAt(4)).hasCoords(2, 0).hasG(2).hasF(7).hasParent(Node(1, 0))
        NodeAssert(closedList.elementAt(5)).hasCoords(2, 1).hasG(3).hasF(7).hasParent(Node(1, 1))
        NodeAssert(closedList.elementAt(6)).hasCoords(0, 2).hasG(2).hasF(7).hasParent(Node(0, 1))
        NodeAssert(closedList.elementAt(7)).hasCoords(2, 2).hasG(4).hasF(7).hasParent(Node(2, 1))
    }

    @Test
    fun render_emptyGrid() {

        // act
        val display = hillClimbingAlgorithm.render(grid)

        // assert
        assertThat(display).isEqualTo(
            """
            ........
            ........
            ........
            ........
            ........
        """.trimIndent()
        )
    }

    @Test
    fun render_step1() {
        // arrange
        hillClimbingAlgorithm.aStar(grid, Node(0, 0), 1)

        // act
        val display = hillClimbingAlgorithm.render(grid)

        // assert
        assertThat(display).isEqualTo(
            """
            CO......
            O.......
            ........
            ........
            ........
        """.trimIndent()
        )
    }

    @Test
    fun renderNode_NodeUnknown_dot() {

        // act
        val char = hillClimbingAlgorithm.renderNode(0, 0)

        // assert
        assertThat(char).isEqualTo('.')
    }

    @Test
    fun renderNode_NodeOpenList_O() {
        // arrange
        hillClimbingAlgorithm.openList.add(Node(0, 0))

        // act
        val char = hillClimbingAlgorithm.renderNode(0, 0)

        // assert
        assertThat(char).isEqualTo('O')
    }

    @Test
    fun renderNode_NodeCloseList_O() {
        // arrange
        hillClimbingAlgorithm.closedList.add(Node(0, 0))

        // act
        val char = hillClimbingAlgorithm.renderNode(0, 0)

        // assert
        assertThat(char).isEqualTo('C')
    }

    @Test
    fun getPath() {
        // arrange
        hillClimbingAlgorithm.aStar(grid, Node(0, 0))

        // act
        val path = hillClimbingAlgorithm.getPath(hillClimbingAlgorithm.findEndNode(grid))

        // assert
        assertThat(path).hasSize(32)
    }

    @Test
    fun renderPath() {
        // arrange
        hillClimbingAlgorithm.aStar(grid, Node(0, 0))
        val path = hillClimbingAlgorithm.getPath(hillClimbingAlgorithm.findEndNode(grid))

        // act
        val display = hillClimbingAlgorithm.renderPath(grid, path)

        // assert
        assertThat(display).isEqualTo(
            """
            XXbXXXXX
            aXcXXXXX
            aXXXXXXX
            acXXXXXX
            abXXXXXX
            
        """.trimIndent()
        )
    }

    @Test
    fun findAllStartingPoints() {

        // act
        val startingPoints = hillClimbingAlgorithm.findAllStartingPoints(grid)

        // assert
        assertThat(startingPoints).containsExactly(
            Node(0, 0),
            Node(0, 1),
            Node(1, 0),
            Node(2, 0),
            Node(3, 0),
            Node(4, 0),
        )
    }

    @Test
    fun findAllTrails() {
        // arrange
        val startingPoints = hillClimbingAlgorithm.findAllStartingPoints(grid)

        // act
        val allTrails = hillClimbingAlgorithm.allTrails(grid, startingPoints)

        // assert
        assertThat(allTrails).hasSize(6)
    }

    @Test
    internal fun findShortestTrail() {

        // act
        val shortestTrail = hillClimbingAlgorithm.findShortestTrail(grid)

        // assert
        assertThat(shortestTrail).isEqualTo(30)
    }

}

class NodeAssert(actual: Node) : AbstractAssert<NodeAssert, Node>(actual, NodeAssert::class.java) {

    companion object {
        fun assertThat(actual: Node): NodeAssert {
            return NodeAssert(actual)
        }
    }

    fun hasRow(row: Int): NodeAssert {
        if (actual.row != row) {
            failWithMessage("Expected node to have ROW [$row] but was [${actual.row}]")
        }
        return this
    }

    fun hasCol(col: Int): NodeAssert {
        if (actual.col != col) {
            failWithMessage("Expected node to have COL [$col] but was [${actual.col}]")
        }
        return this
    }

    fun hasCoords(row: Int, col: Int): NodeAssert {
        hasRow(row)
        hasCol(col)
        return this
    }


    fun hasG(g: Int): NodeAssert {
        if (actual.g != g) {
            failWithMessage("Expected node to have G [$g] but was [${actual.g}]")
        }
        return this
    }

    fun hasF(f: Int): NodeAssert {
        if (actual.f != f) {
            failWithMessage("Expected node to have F [$f] but was [${actual.f}]")
        }
        return this
    }

    fun hasParent(parent: Node): NodeAssert {
        if (actual.parent != parent) {
            failWithMessage("Expected node to have PARENT [$parent] but was [${actual.parent}")
        }
        return this
    }

}
