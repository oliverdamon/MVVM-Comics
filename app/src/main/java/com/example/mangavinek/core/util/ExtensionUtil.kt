package com.example.mangavinek.core.util

import java.util.*

fun String.alphaNumericOnly(): Int {
    val arrayString = split(" ".toRegex())
    val filteredListForNumbers = hashSetOf<Int>()
    arrayString.forEach {
        val onlyNumbers = it.filter { char -> char.isDigit() }
        if (onlyNumbers.isNotEmpty()) {
            filteredListForNumbers.add(onlyNumbers.toInt())
        }
    }

    return Collections.max(filteredListForNumbers)
}