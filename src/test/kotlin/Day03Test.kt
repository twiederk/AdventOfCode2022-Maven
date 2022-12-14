import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

//Lowercase item types a through z have priorities 1 through 26.
//Uppercase item types A through Z have priorities 27 through 52.

class RucksackReorganizationTest {

    @Test
    fun calculatePriority_a_1() {
        // arrange

        // act
        val priority = RucksackReorganization().calculatePriority('a')

        // assert
        assertThat(priority).isEqualTo(1)
    }

    @Test
    fun calculatePriority_z_26() {
        // arrange

        // act
        val priority = RucksackReorganization().calculatePriority('z')

        // assert
        assertThat(priority).isEqualTo(26)
    }

    @Test
    fun calculatePriority_A_27() {
        // arrange

        // act
        val priority = RucksackReorganization().calculatePriority('A')

        // assert
        assertThat(priority).isEqualTo(27)
    }

    @Test
    fun calculatePriority_Z_52() {
        // arrange

        // act
        val priority = RucksackReorganization().calculatePriority('Z')

        // assert
        assertThat(priority).isEqualTo(52)
    }

    @Test
    fun findItem() {
        // arrange

        // act
        val item = RucksackReorganization().findItem("vJrwpWtwJgWrhcsFMMfFFhFp")

        // assert
        assertThat(item).isEqualTo('p')
    }

    @Test
    fun getFirstCompartment() {
        // arrange

        // act
        val firstCompartment = RucksackReorganization().getFirstCompartment("vJrwpWtwJgWrhcsFMMfFFhFp")

        // assert
        assertThat(firstCompartment).isEqualTo("vJrwpWtwJgWr")
    }

    @Test
    fun getSecondCompartment() {
        // arrange

        // act
        val secondCompartment = RucksackReorganization().getSecondCompartment("vJrwpWtwJgWrhcsFMMfFFhFp")

        // assert
        assertThat(secondCompartment).isEqualTo("hcsFMMfFFhFp")
    }

    @Test
    fun loadData() {
        // arrange

        // act
        val rucksack = RucksackReorganization().loadRucksack(Path("src", "test", "resources", "Day03_TestData.txt"))

        // assert
        assertThat(rucksack).hasSize(6)
    }

    @Test
    fun reorganize() {
        // arrange
        val rucksack = RucksackReorganization().loadRucksack(Path("src", "test", "resources", "Day03_TestData.txt"))

        // act
        val sumOfPriorities = RucksackReorganization().reorganize(rucksack)

        // assert
        assertThat(sumOfPriorities).isEqualTo(157)

    }

    @Test
    fun findBadge() {
        // arrange
        val group = listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg"
        )

        // act
        val badge = RucksackReorganization().findBadge(group)

        // assert
        assertThat(badge).isEqualTo('r')
    }

    @Test
    internal fun itemsInBothRucksacks() {
        // arrange
        val rucksack1 = "vJrwpWtwJgWrhcsFMMfFFhFp"
        val rucksack2 = "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"

        // act
        val items = RucksackReorganization().itemsInBothRucksacks(rucksack1, rucksack2)

        // assert
        assertThat(items).isEqualTo("rsFMf")
    }

    @Test
    internal fun reorganizeGroups() {
        // arrange
        val rucksack = RucksackReorganization().loadRucksack(Path("src", "test", "resources", "Day03_TestData.txt"))

        // act
        val sumOfBadges = RucksackReorganization().organizeBadges(rucksack)

        // assert
        assertThat(sumOfBadges).isEqualTo(70)

    }
}