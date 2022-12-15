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
    println("Edge trees: ${trees.countEdgeTrees()}")
    println("Inner visible: ${trees.countVisibleTrees()}")

    println(trees.countEdgeTrees() + trees.countVisibleTrees())
}

data class Tree(val height: Int, val row: Int, val col: Int)

private fun List<List<Tree>>.countEdgeTrees(): Int {
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

private fun List<List<Tree>>.countVisibleTrees(): Int {
    var visibleTrees = 0

    this.forEach { trees ->
        trees.forEach { tree ->
            // If tree is not on edge then check if tree is visible
            if(tree.isOnEdge(rightEdge = trees.size - 1, bottomEdge = this.size - 1).not()){
                val topTrees = this.getTopTrees(tree)
                val isVisibleFromTop = tree.isVisible(topTrees)

                val bottomTrees = this.getBottomTrees(tree)
                val isVisibleFromBottom = tree.isVisible(bottomTrees)

                val leftTrees = this.getLeftTrees(tree)
                val isVisibleFromLeft = tree.isVisible(leftTrees)

                val rightTrees = this.getRightTrees(tree)
                val isVisibleFromRight = tree.isVisible(rightTrees)

                if(isVisibleFromTop || isVisibleFromBottom || isVisibleFromLeft || isVisibleFromRight){
                    visibleTrees++
                }
            }
        }
    }

    return visibleTrees
}

private fun List<List<Tree>>.getTopTrees(startingTree: Tree): List<Tree> {
    val topTrees = mutableListOf<Tree>()

    for(i in startingTree.row - 1 downTo 0){
        topTrees.add(this[i][startingTree.col])
    }

    return topTrees
}

private fun List<List<Tree>>.getBottomTrees(startingTree: Tree): List<Tree> {
    val bottomTrees = mutableListOf<Tree>()

    for(i in startingTree.row + 1 until this.size){
        bottomTrees.add(this[i][startingTree.col])
    }

    return bottomTrees
}

private fun List<List<Tree>>.getLeftTrees(startingTree: Tree): List<Tree> {
    val leftTrees = mutableListOf<Tree>()

    for(i in startingTree.col - 1 downTo 0){
        leftTrees.add(this[startingTree.row][i])
    }

    return leftTrees
}

private fun List<List<Tree>>.getRightTrees(startingTree: Tree): List<Tree> {
    val rightTrees = mutableListOf<Tree>()

    for(i in startingTree.col + 1 until this.first().size){
        rightTrees.add(this[startingTree.row][i])
    }

    return rightTrees
}

private fun Tree.isVisible(treeLine: List<Tree>): Boolean {
    return treeLine.all { this.height > it.height }
}

private fun Tree.isOnEdge(leftEdge: Int = 0, rightEdge: Int, topEdge: Int = 0, bottomEdge: Int): Boolean {
    return this.col == leftEdge || this.col == rightEdge || this.row == topEdge || this.row == bottomEdge
}