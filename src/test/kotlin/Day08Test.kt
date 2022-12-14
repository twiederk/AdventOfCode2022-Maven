import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TreetopTreeHouseTest {

    @Test
    fun convertToInts() {
        // arrange
        val inputAsString = listOf(
            "30373",
            "25512",
            "65332",
        )

        // act
        val inputAsInt = TreetopTreeHouse().convertToInts(inputAsString)

        // assert
        assertThat(inputAsInt[0].size).isEqualTo(5)
        assertThat(inputAsInt.size).isEqualTo(3)
    }

    @Test
    fun loadData() {

        // act
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))

        // assert
        assertThat(rawData).hasSize(5)
    }

    //    30373
//    25512
//    65332
//    33549
//    35390
    @Test
    fun loadAndConvertData() {
        // arrange
        val treetopTreeHouse = TreetopTreeHouse()
        val rawData = treetopTreeHouse.loadData(Path("src", "test", "resources", "Day08_TestData.txt"))

        // act
        val inputAsInt = treetopTreeHouse.convertToInts(rawData)

        // assert
        assertThat(inputAsInt[0][3]).isEqualTo(7)
    }

    @Test
    fun isVisibleFromLeft_11_true() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val visible = TreetopTreeHouse(grid).isVisibleFromLeft(1, 1)

        // assert
        assertThat(visible).isTrue
    }

    @Test
    fun isVisibleFromRight_11_false() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val visible = TreetopTreeHouse(grid).isVisibleFromRight(1, 1)

        // assert
        assertThat(visible).isFalse
    }

    @Test
    fun isVisibleFromTop_11_true() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val visible = TreetopTreeHouse(grid).isVisibleFromTop(1, 1)

        // assert
        assertThat(visible).isTrue
    }

    @Test
    fun isVisibleFromBottom_11_true() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val visible = TreetopTreeHouse(grid).isVisibleFromBottom(1, 1)

        // assert
        assertThat(visible).isFalse
    }

    @Test
    fun isVisible_11_true() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val visible = TreetopTreeHouse(grid).isVisible(1, 1)

        // assert
        assertThat(visible).isTrue
    }

    @Test
    fun isVisible_22_false() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val visible = TreetopTreeHouse(grid).isVisible(2, 2)

        // assert
        assertThat(visible).isFalse
    }

    @Test
    fun isVisible_13_false() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val visible = TreetopTreeHouse(grid).isVisible(1, 3)

        // assert
        assertThat(visible).isFalse
    }

    @Test
    fun isVisible_23_true() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val visible = TreetopTreeHouse(grid).isVisible(2, 3)

        // assert
        assertThat(visible).isTrue
    }

    @Test
    fun isVisible_33_true() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val visible = TreetopTreeHouse(grid).isVisible(3, 3)

        // assert
        assertThat(visible).isFalse
    }

    @Test
    fun countVisibleTrees() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val count = TreetopTreeHouse(grid).countVisibleTrees()

        // assert
        assertThat(count).isEqualTo(21)
    }

    @Test
    fun viewingDistanceUp_12_1() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val viewingDistance = TreetopTreeHouse(grid).viewingDistanceUp(1,2)

        // assert
        assertThat(viewingDistance).isEqualTo(1)
    }

    @Test
    fun viewingDistanceDown_12_2() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val viewingDistance = TreetopTreeHouse(grid).viewingDistanceDown(1,2)

        // assert
        assertThat(viewingDistance).isEqualTo(2)
    }

    @Test
    fun viewingDistanceLeft_12_1() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val viewingDistance = TreetopTreeHouse(grid).viewingDistanceLeft(1,2)

        // assert
        assertThat(viewingDistance).isEqualTo(1)
    }

    @Test
    fun viewingDistanceRight_12_2() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val viewingDistance = TreetopTreeHouse(grid).viewingDistanceRight(1,2)

        // assert
        assertThat(viewingDistance).isEqualTo(2)
    }

    @Test
    fun calculateScenicScore_12_4() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val scenicScore = TreetopTreeHouse(grid).calculatingScenicScore(1,2)

        // assert
        assertThat(scenicScore).isEqualTo(4)
    }

    @Test
    fun calculateScenicScore_32_8() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val scenicScore = TreetopTreeHouse(grid).calculatingScenicScore(3,2)

        // assert
        assertThat(scenicScore).isEqualTo(8)
    }

    @Test
    fun maxScenicScore() {
        // arrange
        val rawData = TreetopTreeHouse().loadData(Path("src", "test", "resources", "Day08_TestData.txt"))
        val grid = TreetopTreeHouse().convertToInts(rawData)

        // act
        val maxScenicScore = TreetopTreeHouse(grid).maxScenicScore()

        // assert
        assertThat(maxScenicScore).isEqualTo(8)

    }
}