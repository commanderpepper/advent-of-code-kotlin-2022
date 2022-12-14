package day07

import readInput
fun main(){
    val fileSystem = readInput("day07")

    val directory = parseFileSystem(Directory(name = "root"), fileSystem)

    println(partOne(directory))

    val totalSpace = 70000000
    val unusedSpaceNeeded = 30000000

    println(partTwo(directory, totalSpace, unusedSpaceNeeded).calculateSize())
}

private fun partTwo(directory: Directory, totalDiskSize: Int, spaceNeededSize: Int): Directory {
    val allDirectories = directory.getAllDirectories()
    val directorySize = directory.calculateSize()
    val spaceAvailable = totalDiskSize - directorySize
    val spaceToMakeUp = spaceNeededSize - spaceAvailable

    val directoriesSuitableForDeletion = allDirectories.filter { it.calculateSize() >= spaceToMakeUp }

    return directoriesSuitableForDeletion.minBy { it.calculateSize() }
}

private fun partOne(directory: Directory): Int {
    val filter = directory.getAllDirectories().filter { it.calculateSize() <= 100000 }
    return filter.sumOf { it.calculateSize() }
}

private fun parseFileSystem(rootDirectory: Directory, fileSystem: List<String>): Directory {
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

    return rootDirectory
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
        this.subdirectories.forEach { (_, value) ->
            result.addAll(value.getAllDirectories())
        }

        return result
    }
}

data class File(val name: String, val size: Int)