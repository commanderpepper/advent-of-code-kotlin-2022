package day13

import readInput

fun main(){
    val day13Input = readInput("day13")
    val packets = parseInput(day13Input)
    packets.forEach(::println)
}

fun parseInput(input: List<String>): List<Packet> {
    val pairs = input.filter { it.isNotBlank() }.chunked(2)
    return pairs.map {
        Packet(it.first(), it.last())
    }
}

data class Packet(val left: String, val right: String)