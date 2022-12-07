package day03

import readInput

fun main() {
    // list of items currently in each rucksack (puzzle input)
    val day3Input = readInput("day03")

    partOne(day3Input)
    partTwo(day3Input)
}

private fun partTwo(day3Input: List<String>) {
    val elfGroups = day3Input.chunked(3).map {
        ElfGroup(it[0], it[1], it[2])
    }
    val commonBadges = elfGroups.map { it.commonBadge }
    val badgePrios = commonBadges.map { it.determinePriority() }

    println(badgePrios.sum())
}

private fun partOne(day3Input: List<String>) {
    val rucksacks = day3Input.map {
        val c1 = it.slice(0 until it.length / 2)
        val c2 = it.slice((it.length / 2) until it.length)
        Rucksack(c1, c2)
    }
    val commonItems = rucksacks.map { it.commonItems }
    val commonItemsPrio = commonItems.map { it.determinePriority() }

    println(commonItemsPrio.sum())
}

data class ElfGroup(val groupOne: String, val groupTwo: String, val groupThree: String){
    val commonBadge = groupOne.toList().intersect(groupTwo.toList()).intersect(groupThree.toList()).first()
}

data class Rucksack(val compartmentOne: String, val compartmentTwo: String){
    val commonItems = compartmentOne.toList().intersect(compartmentTwo.toList()).first()
}

fun Char.determinePriority(): Int {
    return when {
        this.isLowerCase() -> {
            this.code - 96
        }
        this.isUpperCase() -> {
            this.code - 64 + 26
        }
        else -> 0
    }
}