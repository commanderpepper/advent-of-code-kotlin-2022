package day04

import readInput

fun main (){
    val dayFourInput = readInput("day04")
    println(dayFourInput)
    val split = dayFourInput.map {
        it.split(",")
    }
    println(split.size)
    println(split)
    val spaces = split.map { ranges ->
        ranges.map { range ->
            Section(range.first().digitToInt(), range.last().digitToInt())
        }
    }
    spaces.forEach {
        println(it)
    }
    val encapsulates = spaces.count {
        val sectionA = it.first()
        val sectionB = it.last()
        sectionA.contains(sectionB) || sectionB.contains(sectionA)
    }
    println(encapsulates)
}

data class Section(val start: Int, val end: Int){
    fun contains(otherSection: Section): Boolean {
        return this.start <= otherSection.start && this.end >= otherSection.end
    }
}