package com.lojbrooks.munro.domain.repository

import com.lojbrooks.munro.domain.model.Munro

internal interface MunroRepository {
    fun getAllMunros(): List<Munro>
}