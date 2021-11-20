package com.lojbrooks.munro.domain.model

sealed class SortedBy(direction: SortDirection) {
    data class Height(val direction: SortDirection) : SortedBy(direction)
    data class Name(val direction: SortDirection) : SortedBy(direction)
}

enum class SortDirection {
    ASCENDING, DESCENDING
}