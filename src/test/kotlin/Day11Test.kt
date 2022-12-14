import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.math.floor

class MonkeyInTheMiddleTest {

    private val monkey0 = Monkey(
        items = mutableListOf(79, 98),
        operation = { it * 19 },
        divisor = 23,
        throwTo = Pair(2, 3),
        normalizer = { floor(it.toDouble() / 3.0).toLong() }
    )

    private val monkey1 = Monkey(
        items = mutableListOf(54, 65, 75, 74),
        operation = { it + 6 },
        divisor = 19,
        throwTo = Pair(2, 0),
        normalizer = { floor(it.toDouble() / 3.0).toLong() }
    )

    private val monkey2 = Monkey(
        items = mutableListOf(79, 60, 97),
        operation = { it * it },
        divisor = 13,
        throwTo = Pair(1, 3),
        normalizer = { floor(it.toDouble() / 3.0).toLong() }
    )

    private val monkey3 = Monkey(
        items = mutableListOf(74),
        operation = { it + 3 },
        divisor = 17,
        throwTo = Pair(0, 1),
        normalizer = { floor(it.toDouble() / 3.0).toLong() }
    )

    @Test
    fun newWorryLevel() {
        // act
        val worryLevel = monkey0.newWorryLevel(79)

        // assert
        assertThat(worryLevel).isEqualTo(1501)
    }

    @Test
    fun normalizedWorryLevel_item79_result500() {
        // act
        val normalizedWorryLevel = monkey0.normalizedWorryLevel(79)

        // assert
        assertThat(normalizedWorryLevel).isEqualTo(500)
    }

    @Test
    fun normalizedWorryLevel_item98_result620() {
        // act
        val normalizedWorryLevel = monkey0.normalizedWorryLevel(98)

        // assert
        assertThat(normalizedWorryLevel).isEqualTo(620)
    }

    @Test
    fun throwTo() {
        // act
        val throwToMonkey = monkey0.throwToMonkey(500)

        // assert
        assertThat(throwToMonkey).isEqualTo(3)
    }

    @Test
    fun inspect() {
        // act
        val item = monkey0.inspect()

        // assert
        assertThat(item).isEqualTo(79)
        assertThat(monkey0.items).containsExactly(98)
        assertThat(monkey0.inspectCount).isEqualTo(1)
    }

    @Test
    fun catchItem() {

        // act
        monkey0.catchItem(100)

        // assert
        assertThat(monkey0.items).containsExactly(79, 98, 100)
    }

    @Test
    fun playRound() {
        // arrange
        val monkeys = listOf(monkey0, monkey1, monkey2, monkey3)

        // act
        MonkeyInTheMiddle(monkeys).playRound()

        // assert
        assertThat((monkey0.items)).containsExactly(20, 23, 27, 26)
        assertThat((monkey1.items)).containsExactly(2080, 25, 167, 207, 401, 1046)
        assertThat((monkey2.items)).isEmpty()
        assertThat((monkey3.items)).isEmpty()
    }

    @Test
    fun monkey2_throwTo_error() {

        // act
        val throwTo = monkey2.throwToMonkey(2080)

        // assert
        assertThat(throwTo).isEqualTo(1)
    }

    @Test
    fun playRounds() {
        // arrange
        val monkeys = listOf(monkey0, monkey1, monkey2, monkey3)

        // act
        val monkeyBusiness = MonkeyInTheMiddle(monkeys).playRounds(20)

        // assert
        assertThat(monkeyBusiness).isEqualTo(10605)

        assertThat((monkey0.items)).containsExactly(10, 12, 14, 26, 34)
        assertThat((monkey0.inspectCount)).isEqualTo(101)

        assertThat((monkey1.items)).containsExactly(245, 93, 53, 199, 115)
        assertThat((monkey1.inspectCount)).isEqualTo(95)

        assertThat((monkey2.items)).isEmpty()
        assertThat((monkey2.inspectCount)).isEqualTo(7)

        assertThat((monkey3.items)).isEmpty()
        assertThat((monkey3.inspectCount)).isEqualTo(105)
    }

    @Test
    fun playRounds_part2() {
        // arrange
        val monkey0Part2 = monkey0.copy(normalizer = { floor(it.toDouble() % 96_577).toLong() })
        val monkey1Part2 = monkey1.copy(normalizer = { floor(it.toDouble() % 96_577).toLong() })
        val monkey2Part2 = monkey2.copy(normalizer = { floor(it.toDouble() % 96_577).toLong() })
        val monkey3Part2 = monkey3.copy(normalizer = { floor(it.toDouble() % 96_577).toLong() })
        val monkeys = listOf(monkey0Part2, monkey1Part2, monkey2Part2, monkey3Part2)

        // act
        val monkeyBusiness = MonkeyInTheMiddle(monkeys).playRounds(10_000)

        // assert
        assertThat(monkeyBusiness).isEqualTo(2_713_310_158)

    }

}