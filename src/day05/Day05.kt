package day05

import readInput

fun main(){
    val input = readInput("day05")

    val stacks: List<MutableList<Char>> = createStacks(input)
    val instructions: List<Instruction> = parseInstructions(input)

    println(stacks)
    println(instructions)

    println(partOne(instructions, stacks))
}

private fun partOne(
    instructions: List<Instruction>,
    stacks: List<MutableList<Char>>
): String {
    instructions.forEach { instruction ->
        repeat(instruction.amount) {
            val box = stacks[instruction.source].first()
            stacks[instruction.target].add(0, box)
            stacks[instruction.source].removeAt(0)
        }
    }

    println(stacks)
    println(instructions)

    return stacks.topCrates()
}

private fun List<MutableList<Char>>.topCrates(): String {
    return this.map {
        it.firstOrNull() ?: ""
    }.joinToString(separator = "")
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

// Subtract one from source and target to follow instructions from input
private data class Instruction(val amount: Int, val source: Int, val target: Int)