package day11

import readInput
fun main (){
    val day11Input = readInput("day11")
    val monkeys = parseInput(day11Input)

    monkeys.forEach { monkey ->
        // Monkey to move to - index of item to move
        val itemsToMove = mutableListOf<Pair<Int, Int>>()
        monkey.items.forEachIndexed { index, _ ->
            // inspect item
            monkey.updateWorryLevel(index)
            monkey.inspectItem(index)
            if(monkey.isTheHumanWorried(index)){
                itemsToMove.add(monkey.trueMonkey to index)
            }
            else {
                itemsToMove.add(monkey.falseMonkey to index)
            }
        }
        itemsToMove.forEach {
            val otherMonkey = monkeys[it.first]
            otherMonkey.items.add(monkey.items[it.second])
        }
        monkey.items.clear()
    }

    monkeys.forEach(::println)
}

data class Monkey(val items: MutableList<Int>, val operation: Operation, val testCondition: Int, val trueMonkey: Int, val falseMonkey: Int){
    fun inspectItem(itemIndex: Int){
        items[itemIndex] /= 3
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