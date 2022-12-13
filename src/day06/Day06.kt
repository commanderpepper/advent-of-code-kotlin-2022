package day06

import readInput

fun main(){
    val input = readInput("day06")
    val marker = input.first()
    println(partOne(marker))
}

private fun partOne(marker: String): Int {
    var i = 0

    check@ while (i < marker.length - 3) {
        val nextFourChars = marker.substring(i, i + 4)
        println(nextFourChars)
        if (nextFourChars.areAllUnique()) {
            break@check
        } else {
            i++
        }
    }

    return i + 4
}

fun String.areAllUnique(): Boolean {
    return this.toSet().size == this.length
}