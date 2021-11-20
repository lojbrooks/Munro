package com.lojbrooks.munro.data

import com.lojbrooks.munro.domain.model.Munro
import com.lojbrooks.munro.domain.model.MunroType

class MunroMapper {

    fun toDomain(csvLine: String): Munro? {
        val split = csvLine.split(",")
        return if (split.size == EXPECTED_COLUMN_SIZE) {
            val name = split[NAME_INDEX]
            val height = split[HEIGHT_INDEX]
            val gridRef = split[GRID_REF_INDEX]
            val type = split[TYPE_INDEX]
            if (name.isNotBlank()) {
                Munro(
                    name = name,
                    height = height.toDouble(),
                    gridRef = gridRef,
                    type = when (type) {
                        "TOP" -> MunroType.TOP
                        "MUN" -> MunroType.MUNRO
                        else -> null
                    }
                )
            } else null
        } else null
    }

    companion object {
        private const val NAME_INDEX = 6
        private const val HEIGHT_INDEX = 10
        private const val GRID_REF_INDEX = 14
        private const val TYPE_INDEX = 28
        private const val EXPECTED_COLUMN_SIZE = 30
    }
}