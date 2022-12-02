import HandShape.*
import Result.*

//A for Rock
//B for Paper
//C for Scissors

//X for Rock
//Y for Paper
//Z for Scissors

//Rock defeats Scissors
//Scissors defeats Paper
//Paper defeats Rock

//A Y =>
//B X
//C Z
//
//1 for Rock, 2 for Paper, and 3 for Scissors
//0 if you lost, 3 if the round was a draw, and 6 if you won

class RockPaperScissors {

    fun calculateScore(result: Result, handShape: HandShape): Int {
        var score = 0
        score += when (result) {
            LOST -> 0
            DRAW -> 3
            WON -> 6
        }
        score += when (handShape) {
            ROCK -> 1
            PAPER -> 2
            SCISSOR -> 3
        }
        return score
    }

    fun calculateResult(opponentHand: HandShape, myHand: HandShape): Result {
        when (opponentHand) {
            ROCK -> return when (myHand) {
                ROCK -> DRAW
                PAPER -> WON
                SCISSOR -> LOST
            }
            PAPER -> return when (myHand) {
                ROCK -> LOST
                PAPER -> DRAW
                SCISSOR -> WON
            }
            SCISSOR -> return when (myHand) {
                ROCK -> WON
                PAPER -> LOST
                SCISSOR -> DRAW
            }
        }
    }

    fun decryptInput(encryptedInput: List<String>): List<Pair<HandShape, HandShape>> {
        val decryptedInput = mutableListOf<Pair<HandShape, HandShape>>()
        for (input in encryptedInput) {
            val opponentHand = decryptOpponent(input[0])
            val myHand = decryptMe(input[2])
            decryptedInput.add(Pair(opponentHand, myHand))
        }
        return decryptedInput
    }

    private fun decryptOpponent(c: Char): HandShape = when (c) {
        'A' -> ROCK
        'B' -> PAPER
        'C' -> SCISSOR
        else -> throw IllegalArgumentException("Unknown input: [$c]")
    }

    private fun decryptMe(c: Char): HandShape = when (c) {
        'X' -> ROCK
        'Y' -> PAPER
        'Z' -> SCISSOR
        else -> throw IllegalArgumentException("Unknown input: [$c]")
    }

    fun play(strategyGuide: List<String>): Int {
        var totalScore = 0
        val rounds = decryptInput(strategyGuide)
        for (round in rounds) {
            val result = calculateResult(round.first, round.second)
            val score = calculateScore(result, round.second)
            totalScore += score
        }
        return totalScore
    }

}

enum class Result {
    LOST, DRAW, WON
}

enum class HandShape {
    ROCK, PAPER, SCISSOR
}