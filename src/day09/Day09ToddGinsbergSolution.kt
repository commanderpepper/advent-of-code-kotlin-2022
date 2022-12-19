package day09

import readInput
import kotlin.math.absoluteValue
import kotlin.math.sign

// I was unable to solve part two of day nine so I am using Todd's solution to learn more about the problem
// His solution https://todd.ginsberg.com/post/advent-of-code/2022/day9/

fun main(){
    val input = readInput("day09")
    val headPath: String = parseInput(input)
    println(headPath)
    fun solvePart1(): Int = followPath(headPath, 2)
    fun solvePart2(): Int = followPath(headPath, 10)
    println(solvePart1())
    println(solvePart2())
}

private fun parseInput(input: List<String>): String =
    input.joinToString("") { row ->
        val direction = row.substringBefore(" ")
        val numberOfMoves = row.substringAfter(' ').toInt()
        direction.repeat(numberOfMoves)
    }

data class Point(val x: Int = 0, val y: Int = 0) {
    fun move(direction: Char): Point =
        when (direction) {
            'U' -> copy(y = y - 1)
            'D' -> copy(y = y + 1)
            'L' -> copy(x = x - 1)
            'R' -> copy(x = x + 1)
            else -> throw IllegalArgumentException("Unknown Direction: $direction")
        }
}

fun followPathToBeRefactored(headPath: String): Int {
    var head = Point()
    var tail = Point()
    val tailVisits = mutableSetOf(Point())

    headPath.forEach { direction ->
        head = head.move(direction)
        if (head.touches(tail).not()) {
            tail = tail.moveTowards(head)
        }
        tailVisits += tail
    }
    return tailVisits.size
}

fun Point.touches(other: Point): Boolean =
    (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1

fun Point.moveTowards(other: Point): Point =
    Point(
        (other.x - x).sign + x,
        (other.y - y).sign + y
    )

private fun followPath(headPath: String, knots: Int): Int {
    val rope = Array(knots) { Point() }
    val tailVisits = mutableSetOf(Point())

    headPath.forEach { direction ->
        rope[0] = rope[0].move(direction)
        rope.indices.windowed(2, 1) { (head, tail) ->
            if (rope[head].touches(rope[tail]).not()) {
                rope[tail] = rope[tail].moveTowards(rope[head])
            }
        }
        tailVisits += rope.last()
    }
    return tailVisits.size
}