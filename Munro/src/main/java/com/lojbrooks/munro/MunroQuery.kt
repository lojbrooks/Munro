package com.lojbrooks.munro

import com.lojbrooks.munro.data.CsvMunroRepository
import com.lojbrooks.munro.data.CsvParser
import com.lojbrooks.munro.data.MunroMapper
import com.lojbrooks.munro.domain.model.Munro
import com.lojbrooks.munro.domain.model.MunroType
import com.lojbrooks.munro.domain.model.SortDirection
import com.lojbrooks.munro.domain.model.SortedBy
import com.lojbrooks.munro.domain.repository.MunroRepository
import java.util.Comparator

class MunroQuery(filePath: String = DEFAULT_DATA_FILE) {

    private val munroRepository: MunroRepository = CsvMunroRepository(
        parser = CsvParser(filePath),
        mapper = MunroMapper()
    )

    fun query(type: MunroType? = null, sortedBy: SortedBy? = null): List<Munro> {
        return munroRepository.getAllMunros()
            .filter { type == null || it.type == type }
            .sortedWith(getSortComparator(sortedBy))
    }

    private fun getSortComparator(sortedBy: SortedBy?): Comparator<in Munro> {
        return when(sortedBy) {
            is SortedBy.Name -> when(sortedBy.direction) {
                SortDirection.ASCENDING -> compareBy { it.name }
                SortDirection.DESCENDING -> compareByDescending { it.name }
            }
            is SortedBy.Height -> when(sortedBy.direction) {
                SortDirection.ASCENDING -> compareBy { it.height }
                SortDirection.DESCENDING -> compareByDescending { it.height }
            }
            else -> compareBy { 0 }
        }
    }

    companion object {
        private const val DEFAULT_DATA_FILE = "assets/munrotab_v6.2.csv"
    }
}
