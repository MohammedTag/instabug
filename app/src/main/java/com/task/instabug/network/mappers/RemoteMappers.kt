package com.task.instabug.network.mappers

 fun String.ignoreSpecialCharacters(): String {
    val regex = "[^A-Za-z ]".toRegex()
    return regex.replace(this, " ")
}
 fun String.toStringList(): List<String> = this.trim().split("\\s+".toRegex())