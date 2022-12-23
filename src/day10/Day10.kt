package day10

import readInput

fun main(){
    val day10Input = readInput("day10")
    partOne(day10Input)
}

private fun partOne(day10Input: List<String>) {
    var x = 1
    var cycle = 1
    val signals = mutableListOf<Int>()
    var signalCheck = 20

    day10Input.forEach { input ->
        if (input == "noop") {
            cycle++
            if (cycle == signalCheck) {
                signals.add(x * cycle)
                signalCheck += 40
            }
        } else {
            cycle++
            if (cycle == signalCheck) {
                signals.add(x * cycle)
                signalCheck += 40
            }
            val deltaInX = input.split(" ").last().toInt()
            x += deltaInX
            cycle++
            if (cycle == signalCheck) {
                signals.add(x * cycle)
                signalCheck += 40
            }
        }

    }

    println("x is $x")
    println("cycle is $cycle")
    println("signals are $signals")
    println("signal sum is ${signals.sum()}")
}