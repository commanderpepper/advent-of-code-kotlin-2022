package day06

import readInput

fun main(){
    val input = readInput("day06")
    val signal = input.first()
    println(findMarker(signal, 4))
    println(findMarker(signal, 14))
}

private fun findMarker(signal: String, markerSize: Int): Int {
    var i = 0

    check@ while (i < signal.length - markerSize - 1) {
        val packet = signal.substring(i, i + markerSize)
        if (packet.areAllUnique()) {
            break@check
        } else {
            i++
        }
    }

    return i + markerSize
}

fun String.areAllUnique(): Boolean {
    return this.toSet().size == this.length
}