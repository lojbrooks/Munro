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

    fun query(
        type: MunroType? = null,
        sortedBy: SortedBy? = null,
        limit: Int? = null
    ): List<Munro> {
        require(limit == null || limit > 0)
        
        var list = munroRepository.getAllMunros()

        if (type != null) list = list.filter { it.type == type }
        if (sortedBy != null) list = list.sortedWith(getSortComparator(sortedBy))
        if (limit != null) list = list.take(limit)

        return list
    }

    private fun getSortComparator(sortedBy: SortedBy): Comparator<in Munro> {
        return when (sortedBy) {
            is SortedBy.Name -> when (sortedBy.direction) {
                SortDirection.ASCENDING -> compareBy { it.name }
                SortDirection.DESCENDING -> compareByDescending { it.name }
            }
            is SortedBy.Height -> when (sortedBy.direction) {
                SortDirection.ASCENDING -> compareBy { it.height }
                SortDirection.DESCENDING -> compareByDescending { it.height }
            }
        }
    }

    companion object {
        private const val DEFAULT_DATA_FILE = "assets/munrotab_v6.2.csv"
    }
}
