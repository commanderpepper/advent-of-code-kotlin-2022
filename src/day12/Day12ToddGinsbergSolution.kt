package day12

import readInput
import java.util.*

fun main(){
    val dayTwelveInput = readInput("day12")
    val heightMap = parseInput(dayTwelveInput)
    heightMap.elevations.forEach(::println)
    val shortestPath = heightMap.shortestPath(
        begin = heightMap.start,
        isGoal = { it == heightMap.end },
        canMove = { from, to -> to - from <= 1 }
    )
    println(shortestPath)
    val startingElevations = heightMap.findPointsFromElevation { it == 0 }
    println(startingElevations)
    val pathsToEnd = startingElevations.map {
        try {
            heightMap.shortestPath(
                begin = it,
                isGoal = { it == heightMap.end },
                canMove = { from, to -> to - from <= 1 }
            )
        }
        catch (e: Exception){
            Int.MAX_VALUE
        }
    }
    println(pathsToEnd)
    println(pathsToEnd.min())
}

data class Point2D(val x: Int = 0, val y: Int = 0) {
    fun cardinalNeighbors(): Set<Point2D> =
        setOf(
            copy(x = x - 1),
            copy(x = x + 1),
            copy(y = y - 1),
            copy(y = y + 1)
        )
}

private class HeightMap(val elevations: Map<Point2D, Int>, val start: Point2D, val end: Point2D) {
    fun shortestPath(
        begin: Point2D,
        isGoal: (Point2D) -> Boolean,
        canMove: (Int, Int) -> Boolean
    ): Int {
        val seen = mutableSetOf<Point2D>()
        val queue = PriorityQueue<PathCost>().apply { add(PathCost(begin, 0)) }

        while (queue.isNotEmpty()) {
            val nextPoint = queue.poll()

            if (nextPoint.point !in seen) {
                seen += nextPoint.point
                val neighbors = nextPoint.point.cardinalNeighbors()
                    .filter { it in elevations }
                    .filter { canMove(elevations.getValue(nextPoint.point), elevations.getValue(it)) }
                if (neighbors.any { isGoal(it) }) return nextPoint.cost + 1
                queue.addAll(neighbors.map { PathCost(it, nextPoint.cost + 1) })
            }
        }
        throw IllegalStateException("No valid path found")
    }

    /**
     * Quick solution I came to when I first read part two of day twelve.
     */
    fun findPointsFromElevation(meetsConditon: (Int) -> Boolean): List<Point2D>{
        return elevations.filter { meetsConditon(it.value) }.keys.toList()
    }
}

private fun parseInput(input: List<String>): HeightMap {
    var start: Point2D? = null
    var end: Point2D? = null
    val elevations = input.flatMapIndexed { y, row ->
        row.mapIndexed { x, char ->
            val here = Point2D(x, y)
            here to when (char) {
                'S' -> 0.also { start = here }
                'E' -> 25.also { end = here }
                else -> char - 'a'
            }
        }
    }.toMap()
    return HeightMap(elevations, start!!, end!!)
}

private data class PathCost(val point: Point2D, val cost: Int) : Comparable<PathCost> {
    override fun compareTo(other: PathCost): Int =
        this.cost.compareTo(other.cost)
}
