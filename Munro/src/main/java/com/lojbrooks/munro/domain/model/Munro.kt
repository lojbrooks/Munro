package com.lojbrooks.munro.domain.model

data class Munro(
    val name: String,
    val height: Double,
    val gridRef: String,
    val type: MunroType?
)

enum class MunroType {
    MUNRO, TOP
}
