package com.lojbrooks.munro.data

import com.lojbrooks.munro.domain.model.Munro
import com.lojbrooks.munro.domain.repository.MunroRepository

internal class CsvMunroRepository(parser: CsvParser, mapper: MunroMapper) : MunroRepository {

    private val data = parser.readLines().mapNotNull { mapper.toDomain(it) }

    override fun getAllMunros(): List<Munro> {
        return data
    }
}