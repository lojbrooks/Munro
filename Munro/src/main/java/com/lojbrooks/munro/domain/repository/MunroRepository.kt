package com.lojbrooks.munro.domain.repository

import com.lojbrooks.munro.domain.model.Munro

interface MunroRepository {
    fun getAllMunros(): List<Munro>
}