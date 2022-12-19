package day09

import readInput
import kotlin.math.abs

fun main(){
    val dayNineInput = readInput("day09")
    println("Tail visited ${partOne(dayNineInput)}")
}

private fun partOne(dayNineInput: List<String>): Int {
    val tailVisitedPositions = mutableSetOf<Position>()
    var tail = Knot(Position(xPosition = XPosition(0), yPosition = YPosition(0)))
    var head = Knot(Position(xPosition = XPosition(0), yPosition = YPosition(0)))

    tailVisitedPositions.add(tail.position)

    dayNineInput.forEach {
        val instruction = it.split(" ")
        val direction = instruction.first()
        val movement = instruction.last()

        repeat(movement.toInt()) {
            val currentHeadPosition = head.position
            when (direction) {
                // Going Right
                "R" -> {
                    head = head.copy(position = currentHeadPosition.copy(xPosition = XPosition(currentHeadPosition.xPosition.position + 1)))
                    if (areKnotsNextToEachOther(head, tail).not()) {
                        tail = tail.copy(position = currentHeadPosition)
                    }
                }
                // Going Left
                "L" -> {
                    head = head.copy(position = currentHeadPosition.copy(xPosition = XPosition(currentHeadPosition.xPosition.position - 1)))
                    if (areKnotsNextToEachOther(head, tail).not()) {
                        tail = tail.copy(position = currentHeadPosition)
                    }
                }
                // Going Up
                "U" -> {
                    head = head.copy(position = currentHeadPosition.copy(yPosition = YPosition(currentHeadPosition.yPosition.position + 1)))
                    if (areKnotsNextToEachOther(head, tail).not()) {
                        tail = tail.copy(position = currentHeadPosition)
                    }
                }
                // Going Down
                else -> {
                    head = head.copy(position = currentHeadPosition.copy(yPosition = YPosition(currentHeadPosition.yPosition.position - 1)))
                    if (areKnotsNextToEachOther(head, tail).not()) {
                        tail = tail.copy(position = currentHeadPosition)
                    }
                }
            }
            tailVisitedPositions.add(tail.position)
        }

    }

    return tailVisitedPositions.size
}

fun areKnotsNextToEachOther(head: Knot, tail: Knot): Boolean {
    // check if on top of one another
    if(head.position == tail.position){
        return true
    }

    val xDistance = abs(head.position.xPosition.position - tail.position.xPosition.position)
    val yDistance = abs(head.position.yPosition.position - tail.position.yPosition.position)

    val nextToEachOthersXCoordinate = xDistance == 1 || xDistance == 0
    val nextToEachOthersYCoordinate = yDistance == 1 || yDistance == 0

    // If the xDistance and yDistance are both 1 then they are next to each other.
    if(nextToEachOthersXCoordinate && nextToEachOthersYCoordinate){
        return true
    }

    return false
}
data class Knot(val position: Position)

data class Position(val xPosition: XPosition, val yPosition: YPosition)

@JvmInline
value class XPosition(val position: Int)

@JvmInline
value class YPosition(val position: Int)