package day12

import readInput
import kotlin.math.abs

fun main(){
    val day12Input = readInput("day12")
    day12Input.forEach(::println)
    val elevationMap = generateElevationMap(day12Input)
    elevationMap.forEach(::println)
    val startPosition = day12Input.findPositionOf('S')
    val endPosition = day12Input.findPositionOf('E')
    println(startPosition)
    println(endPosition)

    val rootPosition = PositionTree(rootPosition = startPosition!!)

    // Track if position tree was already checked
    val visitedPositions = mutableSetOf<Position>()
    val positionsToCheck = mutableListOf<PositionTree>()
    positionsToCheck.add(rootPosition)

    // While there are more positions to check, generate children
    while(positionsToCheck.isNotEmpty()){
        val positionTreeToGenerateFrom = positionsToCheck.removeFirst()
        // If the next item in the list is not in the set then generate away
        if(visitedPositions.contains(positionTreeToGenerateFrom.rootPosition).not()){
            generateChildren(positionTreeToGenerateFrom, elevationMap)
            visitedPositions.add(positionTreeToGenerateFrom.rootPosition)

            // If a child is not null then add it to the list of positions to check against
            positionTreeToGenerateFrom.leftChild?.let { positionsToCheck.add(it) }
            positionTreeToGenerateFrom.rightChild?.let { positionsToCheck.add(it) }
            positionTreeToGenerateFrom.upChild?.let { positionsToCheck.add(it) }
            positionTreeToGenerateFrom.downChild?.let { positionsToCheck.add(it) }
        }
    }

    println(rootPosition)
}

fun generateChildren(positionTree: PositionTree?, elevationMap: List<List<Int>>){
    if(positionTree != null){
        val leftPosition: Position = positionTree.rootPosition.copy(x = positionTree.rootPosition.x - 1 , y = positionTree.rootPosition.y )
        val rightPosition: Position = positionTree.rootPosition.copy(x = positionTree.rootPosition.x + 1 , y = positionTree.rootPosition.y)
        val upPosition: Position = positionTree.rootPosition.copy(x = positionTree.rootPosition.x , y = positionTree.rootPosition.y - 1)
        val downPosition: Position = positionTree.rootPosition.copy(x = positionTree.rootPosition.x , y = positionTree.rootPosition.y + 1)

        val currentPositionElevationValue = elevationMap[positionTree.rootPosition.y][positionTree.rootPosition.x]
        // Left
        addChildPosition(leftPosition, elevationMap, currentPositionElevationValue,leftPosition.x >= 0){ positionToAdd ->
            positionTree.leftChild = PositionTree(positionToAdd)
        }

        // Right
        addChildPosition(rightPosition, elevationMap, currentPositionElevationValue,rightPosition.x < elevationMap.first().size){ positionToAdd ->
            positionTree.rightChild = PositionTree(positionToAdd)
        }

        // Up
        addChildPosition(upPosition, elevationMap, currentPositionElevationValue,upPosition.y >= 0){ positionToAdd ->
            positionTree.upChild = PositionTree(positionToAdd)
        }

        // Down
        addChildPosition(downPosition, elevationMap, currentPositionElevationValue,downPosition.y < elevationMap.size){ positionToAdd ->
            positionTree.downChild = PositionTree(positionToAdd)
        }
    }
}

private fun addChildPosition(
    positionToInspect: Position,
    elevationMap: List<List<Int>>,
    currentPositionElevationValue: Int,
    positionCheck: Boolean,
    positionAdd: (Position) -> Unit
) {
    if (positionCheck) {
        val candidateElevationValue = elevationMap[positionToInspect.y][positionToInspect.x]
        if (candidateElevationValue == currentPositionElevationValue || abs(candidateElevationValue - currentPositionElevationValue) == 1) {
            positionAdd(positionToInspect)
        }
    }
}

fun generateElevationMap(heightMap: List<String>, startChar: Char = 'S', endChar: Char = 'E'): List<List<Int>> {
    val elevationMap = mutableListOf<List<Int>>()

    heightMap.forEach { row ->
        val list = mutableListOf<Int>()
        row.forEach { elevation ->
            list.add(
                when (elevation) {
                    startChar -> 'a'.calculateElevation()
                    endChar -> 'z'.calculateElevation()
                    else -> elevation.calculateElevation()
                }
            )
        }
        elevationMap.add(list)
    }

    return elevationMap.toList()
}

fun List<String>.findPositionOf(targetChar: Char): Position? {
    var position: Position? = null

    this.forEachIndexed { y, row ->
        row.forEachIndexed { x, elevation ->
            if(elevation == targetChar){
                position = Position(x = x, y = y)
            }
        }
    }

    return position
}

fun Char.calculateElevation(): Int {
    return this.toInt() - 96
}

data class Position(val x: Int, val y: Int)

data class PositionTree(
    val rootPosition: Position,
    var leftChild: PositionTree? = null,
    var rightChild: PositionTree? = null,
    var upChild: PositionTree? = null,
    var downChild: PositionTree? = null
)