package day07

import readInput
fun main(){
    val fileSystem = readInput("day07")

    val rootDirectory = Directory(name = "root")
    var workingDirectory = rootDirectory

    fileSystem.drop(1).forEach { terminalOutput ->
        if(terminalOutput.substring(0, 4) == "$ cd"){
            if(terminalOutput.substringAfter("$ cd ") == ".."){
                if(workingDirectory.parentDirectory != null){
                    workingDirectory = workingDirectory.parentDirectory!!
                }
            }
            else {
                val directoryName = terminalOutput.substringAfter("$ cd ")
                if(workingDirectory.subdirectories.contains(directoryName)){
                    workingDirectory = workingDirectory.subdirectories[directoryName]!!
                }
            }
        }

        // Handle adding subdirectories or files
        if(terminalOutput.substring(0, 3) == "dir"){
            val directoryName = terminalOutput.substringAfter("dir ")
            val childDirectory = Directory(name = directoryName)
            childDirectory.parentDirectory = workingDirectory
            workingDirectory.subdirectories[directoryName] = childDirectory
        }
        else if(terminalOutput.first().isDigit()){
            val file = terminalOutput.split(" ")
            val fileSize = file.first()
            val fileName = file.last()
            workingDirectory.files.add(File(fileName, fileSize.toInt()))
        }
    }

    println(rootDirectory)
}

data class Directory(
    val name: String,
    val files: MutableList<File> = mutableListOf(),
    val subdirectories: MutableMap<String, Directory> = mutableMapOf()){
    var parentDirectory: Directory? = null
}

data class File(val name: String, val size: Int)