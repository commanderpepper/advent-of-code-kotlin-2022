package day07

import readInput
fun main(){
    val fileSystem = readInput("day07")

    val rootDirectory = Directory(name = "root")

    println(partOne(fileSystem, rootDirectory))
}

private fun partOne(
    fileSystem: List<String>,
    rootDirectory: Directory
): Int {
    var workingDirectory = rootDirectory

    fileSystem.drop(1).forEach { terminalOutput ->
        if (terminalOutput.substring(0, 4) == "$ cd") {
            if (terminalOutput.substringAfter("$ cd ") == "..") {
                if (workingDirectory.parentDirectory != null) {
                    workingDirectory = workingDirectory.parentDirectory!!
                }
            } else {
                val directoryName = terminalOutput.substringAfter("$ cd ")
                if (workingDirectory.subdirectories.contains(directoryName)) {
                    workingDirectory = workingDirectory.subdirectories[directoryName]!!
                }
            }
        }

        // Handle adding subdirectories or files
        if (terminalOutput.substring(0, 3) == "dir") {
            val directoryName = terminalOutput.substringAfter("dir ")
            val childDirectory = Directory(name = directoryName)
            childDirectory.parentDirectory = workingDirectory
            workingDirectory.subdirectories[directoryName] = childDirectory
        } else if (terminalOutput.first().isDigit()) {
            val file = terminalOutput.split(" ")
            val fileSize = file.first()
            val fileName = file.last()
            workingDirectory.files.add(File(fileName, fileSize.toInt()))
        }
    }

    val filter = rootDirectory.getAllDirectories().filter { it.calculateSize() <= 100000 }
    return filter.sumOf { it.calculateSize() }
}

data class Directory(
    val name: String,
    val files: MutableList<File> = mutableListOf(),
    val subdirectories: MutableMap<String, Directory> = mutableMapOf()){
    var parentDirectory: Directory? = null
    fun calculateSize() : Int {
        return files.sumOf { it.size } + if(subdirectories.isNotEmpty()) subdirectories.values.sumOf { it.calculateSize() } else 0
    }

    fun getAllDirectories(): List<Directory>{
        val result = mutableListOf<Directory>()
        result.add(this)
        this.subdirectories.forEach { (key, value) ->
            result.addAll(value.getAllDirectories())
        }

        return result
    }
}

fun traverseDirectory(directoryList: List<Directory>, predicate: (Directory) -> Boolean): List<Directory>{
    return directoryList.filter(predicate)
}

data class File(val name: String, val size: Int)