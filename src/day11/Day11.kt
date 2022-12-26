package day11

import readInput
fun main (){
    val day11Input = readInput("day11")
    println(monkeyBusiness(parseInput(day11Input)))
}

private fun monkeyBusiness(monkeys: List<Monkey>, rounds: Int = 20, worryDivisor: Int = 3): Int {
    repeat(rounds) {
        monkeys.forEach { monkey ->
            // Monkey to move to - index of item to move
            val itemsToMove = mutableListOf<Pair<Int, Int>>()
            monkey.items.forEachIndexed { index, _ ->
                // Make the human worried
                monkey.updateWorryLevel(index)
                // Monkey inspects item
                monkey.inspectItem(index, worryDivisor)
                // Monkey decided who it's going to throw to
                if (monkey.isTheHumanWorried(index)) {
                    itemsToMove.add(monkey.trueMonkey to index)
                } else {
                    itemsToMove.add(monkey.falseMonkey to index)
                }
            }
            // Monkey throws the items
            itemsToMove.forEach {
                val otherMonkey = monkeys[it.first]
                otherMonkey.items.add(monkey.items[it.second])
            }
            monkey.items.clear()
        }
    }
//    monkeys.forEach(::println)

    val deviousMonkeys = monkeys.sortedByDescending { it.itemsInspected }
    return deviousMonkeys[0].itemsInspected * deviousMonkeys[1].itemsInspected
}

data class Monkey(val items: MutableList<Int>, val operation: Operation, val testCondition: Int, val trueMonkey: Int, val falseMonkey: Int, var itemsInspected: Int = 0){
    fun inspectItem(itemIndex: Int, divisor : Int = 3){
        items[itemIndex] = if(divisor > 0) items[itemIndex] / divisor else items[itemIndex]
        itemsInspected++
    }

    fun updateWorryLevel(itemIndex: Int){
        items[itemIndex] = operation.operate(items[itemIndex])
    }

    fun isTheHumanWorried(itemIndex: Int): Boolean {
        return items[itemIndex] % testCondition == 0
    }
}

data class Operation(private val type: String, private val amount: Int, val useOld: Boolean){
    fun operate(item: Int): Int {
        return if(useOld){
            when(type){
                "*" -> item * item
                else -> item + item
            }
        }
        else {
            when(type){
                "*" -> item * amount
                else -> item + amount
            }
        }
    }
}

fun parseInput(input: List<String>): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    var i = 0
    while(i < input.size){
        // If the input is M I assume it's a Monkey
        if(input[i].isNotEmpty() && input[i].first() == 'M'){
            val startItemInputStartIndex = input[i + 1].indexOfFirst { it.isDigit() }
            val startingItems = input[i + 1].slice(startItemInputStartIndex until input[i + 1].length).split(",").map(String::trim).map(String::toInt)
//            println(startingItems)

            val operationStartIndex = if(input[i + 2].contains("*")) input[i + 2].indexOf("*") else input[i + 2].indexOf("+")
            val operationList = input[i + 2].slice(operationStartIndex until input[i + 2].length).split(" ")
            val operation = Operation(type = operationList.first(), useOld = operationList.last() == "old", amount = if(operationList.last() == "old") 1 else operationList.last().toInt())
//            println(operation)

            val testStartIndex = input[i + 3].indexOfFirst { it.isDigit() }
            val testAmount = input[i + 3].slice(testStartIndex until input[i + 3].length).toInt()
//            println(testAmount)

            val trueStartIndex = input[i + 4].indexOfFirst { it.isDigit() }
            val trueMonkey = input[i + 4].slice(trueStartIndex until input[i + 4].length).toInt()
//            println(trueMonkey)

            val falseStartIndex = input[i + 5].indexOfFirst { it.isDigit() }
            val falseMonkey = input[i + 5].slice(falseStartIndex until input[i + 5].length).toInt()
//            println(falseMonkey)

            val monkey = Monkey(items = startingItems.toMutableList(), operation = operation, testCondition = testAmount, trueMonkey = trueMonkey, falseMonkey = falseMonkey)
            monkeys.add(monkey)

            i += 5
        }
        i++
    }

    return monkeys
}