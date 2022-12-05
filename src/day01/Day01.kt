package day01

import readInput

fun main() {
    val caloriesList = readInput("day01")
    val elfCalories = mutableListOf<Int>()

    var index = 0

    for (i in caloriesList.indices) {
        if (caloriesList[i].isEmpty()) {
            index++
        } else {
            if (elfCalories.elementAtOrNull(index) != null) {
                val current = elfCalories[index]
                elfCalories[index] = caloriesList[i].toInt() + current
            } else {
                elfCalories.add(caloriesList[i].toInt())
            }
        }
    }

    println(elfCalories)

    var hungriestElf = 0
    var highestCalorieCount = Int.MIN_VALUE

    elfCalories.forEachIndexed { elf, calorie ->
        if (calorie > highestCalorieCount) {
            hungriestElf = elf + 1
            highestCalorieCount = calorie
        }
    }

    println(hungriestElf)
}
