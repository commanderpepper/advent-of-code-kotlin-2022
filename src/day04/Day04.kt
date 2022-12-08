package day04

import readInput

fun main (){
    val dayFourInput = readInput("day04")
    val ranges: List<Pair<IntRange,IntRange>> = dayFourInput.map { it.asRanges() }
    println(ranges.count { it.first fullyOverlaps it.second || it.second fullyOverlaps it.first  })
}
private fun String.asIntRange(): IntRange =
    substringBefore("-").toInt() .. substringAfter("-").toInt()

private fun String.asRanges(): Pair<IntRange,IntRange> =
    substringBefore(",").asIntRange() to substringAfter(",").asIntRange()
private infix fun IntRange.fullyOverlaps(other: IntRange): Boolean =
    first <= other.first && last >= other.last

data class Section(val start: Int, val end: Int){
    fun contains(otherSection: Section): Boolean {
        return this.start <= otherSection.start && this.end >= otherSection.end
    }
}