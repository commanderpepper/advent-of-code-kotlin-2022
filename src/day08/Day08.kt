package day08

import readInput

fun main(){
    val day08Input = readInput("day08")
    println(day08Input)
    val trees : List<List<Tree>> = day08Input.mapIndexed {rowIndex, row ->
        row.mapIndexed { colIndex, height ->
            Tree(height = height.toString().toInt(), row = rowIndex, col = colIndex)
        }
    }
    println(trees)
    println(trees.countEdgeTrees())
}

data class Tree(val height: Int, val row: Int, val col: Int)

fun List<List<Tree>>.countEdgeTrees(): Int {
    var edgeTrees = 0

    this.forEach { trees ->
        trees.forEach { tree ->
            if(tree.isOnEdge(rightEdge = trees.size - 1, bottomEdge = this.size - 1)){
                edgeTrees++
            }
        }
    }

    return edgeTrees
}

private fun Tree.isOnEdge(leftEdge: Int = 0, rightEdge: Int, topEdge: Int = 0, bottomEdge: Int): Boolean {
    return this.col == leftEdge || this.col == rightEdge || this.row == topEdge || this.row == bottomEdge
}