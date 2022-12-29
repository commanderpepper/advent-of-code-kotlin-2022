package day12

import readInput

fun main(){
    val day12Input = readInput("day12")
    day12Input.forEach(::println)
    val elevationMap = generateElevationMap(day12Input)
    elevationMap.forEach(::println)
    val startPosition = day12Input.findPositionOf('S')
    val endPosition = day12Input.findPositionOf('E')
    println(startPosition)
    println(endPosition)
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