package com.lojbrooks.munro.data

import java.io.File

internal class CsvParser(private val filePath: String) {

    fun readLines(): List<String> {
        val data = mutableListOf<String>()
        File(filePath).inputStream().bufferedReader().useLines { lines ->
            lines.forEachIndexed { i, line ->
                if (i != 0) data.add(line)
            }
        }
        return data
    }
}