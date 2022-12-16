package day09

import readInput
import kotlin.math.abs

fun main(){
    val dayNineInput = readInput("day09")
    val tailVisitedPositions = mutableSetOf<day09.Position>()
    var tail = Knot(day09.Position(xPosition = XPosition(0), yPosition = YPosition(2)))
    var head = Knot(day09.Position(xPosition = XPosition(0), yPosition = YPosition(0)))

    println(areKnotsNextToEachOther(head, tail))

    tailVisitedPositions.add(tail.position)

    dayNineInput.forEach {
        val instruction = it.split(" ")
        val direction = it.first()
        val movement = it.last()
    }

    println(tailVisitedPositions.size)
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