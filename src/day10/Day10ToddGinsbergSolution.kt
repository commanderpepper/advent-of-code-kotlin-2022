package day10

import readInput
import kotlin.math.absoluteValue

fun main() {
    val day10Input = readInput("day10")
    val signals = parseInput(day10Input).runningReduce(Int::plus)
    val dayOneSolution = solvePart1(signals)
    println(dayOneSolution)

    solvePart2(signals)
}

fun parseInput(input: List<String>): List<Int> =
    buildList {
        add(1)
        input.forEach { line ->
            add(0)
            if (line.startsWith("addx")) {
                add(line.substringAfter(" ").toInt())
            }
        }
    }

fun List<Int>.sampleSignals(): List<Int> =
    (60 .. size step 40).map { cycle -> cycle * this[cycle - 1] } + this[19]

fun solvePart1(signals: List<Int>): Int = signals.sampleSignals().sum()

fun solvePart2(signals: List<Int>): Unit = signals.screen().print()

fun List<Int>.screen(): List<Boolean> = this.mapIndexed { pixel, signal -> (signal-(pixel%40)).absoluteValue <= 1 }

fun List<Boolean>.print() {
    this.windowed(40, 40, false).forEach { row ->
        row.forEach { pixel ->
            print(if(pixel) '#' else ' ')
        }
        println()
    }
}
