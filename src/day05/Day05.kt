package day05

import readInput

fun main(){
    val input = readInput("day05")

    val stacks: List<MutableList<Char>> = createStacks(input)
    val instructions: List<Instruction> = parseInstructions(input)

    println(stacks)
    println(instructions)
}

private fun createStacks(input: List<String>): List<MutableList<Char>> {
    val stackRows = input.takeWhile { it.contains('[') }
    return (1..stackRows.last().length step 4).map { index ->
        stackRows
            .mapNotNull { it.getOrNull(index) }
            .filter { it.isUpperCase() }
            .toMutableList()
    }
}

private fun parseInstructions(input: List<String>): List<Instruction> =
    input
        .dropWhile { !it.startsWith("move") }
        .map { row ->
            row.split(" ").let { parts ->
                Instruction(parts[1].toInt(), parts[3].toInt() - 1, parts[5].toInt() - 1)
            }
        }

private data class Instruction(val amount: Int, val source: Int, val target: Int)