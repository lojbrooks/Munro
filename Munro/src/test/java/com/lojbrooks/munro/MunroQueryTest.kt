package com.lojbrooks.munro

import com.lojbrooks.munro.domain.model.MunroType
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test

class MunroQueryTest {

    private val dataSet1 = MunroQuery("src/test/assets/test_data_1.csv")
    private val dataSet2 = MunroQuery("src/test/assets/test_data_2.csv")

    @Test
    fun `GIVEN no filter WHEN query THEN return all data`() {
        assertThat(dataSet1.query().size, equalTo(10))
        assertThat(dataSet2.query().size, equalTo(20))
    }

    @Test
    fun `GIVEN filter by munro WHEN query THEN return only munros`() {
        val result1 = dataSet1.query(type = MunroType.MUNRO)
        assertThat(result1.size, equalTo(6))
        assertTrue(result1.all { it.type == MunroType.MUNRO })

        val result2 = dataSet2.query(type = MunroType.MUNRO)
        assertThat(result2.size, equalTo(13))
        assertTrue(result2.all { it.type == MunroType.MUNRO })
    }

    @Test
    fun `GIVEN filter by munro top WHEN query THEN return only munro tops`() {
        val result1 = dataSet1.query(type = MunroType.TOP)
        assertThat(result1.size, equalTo(2))
        assertTrue(result1.all { it.type == MunroType.TOP })

        val result2 = dataSet2.query(type = MunroType.TOP)
        assertThat(result2.size, equalTo(4))
        assertTrue(result2.all { it.type == MunroType.TOP })
    }
}