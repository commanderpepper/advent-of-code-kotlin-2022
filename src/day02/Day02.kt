package day02

import readInput

fun main() {
    val rockPaperScissors = readInput("day02")
    val split = rockPaperScissors.map {
        val split = it.split(" ")
        split.last() to split.first()
    }
    println(split.sumOf { it.determinePoints() })
    println(split.sumOf { it.determinePointsFromOutcome() })
}

/**
 * A, X - Rock (1)
 * B, Y - Paper (2)
 * C, Z - Scissor (3)
 */
fun Pair<String, String>.determinePoints(): Int {
    return when (this.first) {
        "X" -> 1 + when (this.second) {
            "A" -> 3
            "B" -> 0
            "C" -> 6
            else -> 0
        }

        "Y" -> 2 + when (this.second) {
            "A" -> 6
            "B" -> 3
            "C" -> 0
            else -> 0
        }

        "Z" -> 3 + when (this.second) {
            "A" -> 0
            "B" -> 6
            "C" -> 3
            else -> 0
        }

        else -> 0
    }
}

/**
 * Outcomes (first column)
 * X - lose (0)
 * Y - draw (3)
 * Z - Win (6)
 *
 * A - Rock (1)
 * B - Paper (2)
 * C - Scissor (3)
 */
fun Pair<String, String>.determinePointsFromOutcome(): Int {
    return when(this.first){
        "X" -> 0 + when(this.second){
            "A" -> 3
            "B" -> 1
            "C" -> 2
            else -> 0
        }
        "Y" -> 3 + when(this.second){
            "A" -> 1
            "B" -> 2
            "C" -> 3
            else -> 0
        }
        "Z" -> 6 + when(this.second){
            "A" -> 2
            "B" -> 3
            "C" -> 1
            else -> 0
        }
        else -> 0
    }
}