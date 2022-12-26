package day10

import readInput

fun main() {
    val day10Input = readInput("day10")
    val signals = parseInput(day10Input).runningReduce(Int::plus)
    val dayOneSolution = solvePart1(signals)
    println(dayOneSolution)
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