import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day02RockPaperScissorsTest {

    @Test
    fun calculateScore_WonPaper_Result8() {

        // act
        val score = RockPaperScissors().calculateScore(Result.WON, HandShape.PAPER)

        // assert
        assertThat(score).isEqualTo(8)
    }

    @Test
    fun calculateScore_LostRock_1() {

        // act
        val score = RockPaperScissors().calculateScore(Result.LOST, HandShape.ROCK)

        // assert
        assertThat(score).isEqualTo(1)
    }

    @Test
    fun calculateScore_DrawScissors_6() {

        // act
        val score = RockPaperScissors().calculateScore(Result.DRAW, HandShape.SCISSOR)

        // assert
        assertThat(score).isEqualTo(6)
    }

    @Test
    fun calculateResult_RockScissor_Lost() {
        // arrange

        // act
        val result = RockPaperScissors().calculateResult(HandShape.ROCK, HandShape.SCISSOR)

        // assert
        assertThat(result).isEqualTo(Result.LOST)
    }

    @Test
    fun calculateResult_ScissorPaper_Lost() {
        // arrange

        // act
        val result = RockPaperScissors().calculateResult(HandShape.SCISSOR, HandShape.PAPER)

        // assert
        assertThat(result).isEqualTo(Result.LOST)
    }

    @Test
    fun calculateResult_PaperRock_Lost() {
        // arrange

        // act
        val result = RockPaperScissors().calculateResult(HandShape.PAPER, HandShape.ROCK)

        // assert
        assertThat(result).isEqualTo(Result.LOST)
    }

    @Test
    fun calculateResult_ScissorRock_WON() {
        // arrange

        // act
        val result = RockPaperScissors().calculateResult(HandShape.SCISSOR, HandShape.ROCK)

        // assert
        assertThat(result).isEqualTo(Result.WON)
    }

    @Test
    fun calculateResult_PaperScissor_Lost() {
        // arrange

        // act
        val result = RockPaperScissors().calculateResult(HandShape.PAPER, HandShape.SCISSOR)

        // assert
        assertThat(result).isEqualTo(Result.WON)
    }

    @Test
    fun calculateResult_RockPaper_Lost() {
        // arrange

        // act
        val result = RockPaperScissors().calculateResult(HandShape.ROCK, HandShape.PAPER)

        // assert
        assertThat(result).isEqualTo(Result.WON)
    }

    @Test
    fun calculateResult_RockRock_Draw() {
        // arrange

        // act
        val result = RockPaperScissors().calculateResult(HandShape.ROCK, HandShape.ROCK)

        // assert
        assertThat(result).isEqualTo(Result.DRAW)
    }

    @Test
    fun calculateResult_PaperPaper_Draw() {
        // arrange

        // act
        val result = RockPaperScissors().calculateResult(HandShape.PAPER, HandShape.PAPER)

        // assert
        assertThat(result).isEqualTo(Result.DRAW)
    }

    @Test
    fun calculateResult_ScissorScissor_Draw() {
        // arrange

        // act
        val result = RockPaperScissors().calculateResult(HandShape.SCISSOR, HandShape.SCISSOR)

        // assert
        assertThat(result).isEqualTo(Result.DRAW)
    }

}
