package com.lojbrooks.munro

import com.lojbrooks.munro.data.CsvMunroRepository
import com.lojbrooks.munro.data.CsvParser
import com.lojbrooks.munro.data.MunroMapper
import com.lojbrooks.munro.domain.model.Munro
import com.lojbrooks.munro.domain.model.MunroType
import com.lojbrooks.munro.domain.repository.MunroRepository

class MunroQuery(filePath: String = DEFAULT_DATA_FILE) {

    private val munroRepository: MunroRepository = CsvMunroRepository(
        parser = CsvParser(filePath),
        mapper = MunroMapper()
    )

    fun query(type: MunroType? = null): List<Munro> {
        return munroRepository.getAllMunros()
            .filter { type == null || it.type == type }
    }

    companion object {
        private const val DEFAULT_DATA_FILE = "assets/munrotab_v6.2.csv"
    }
}
