package com.lojbrooks.munro

import com.lojbrooks.munro.domain.model.MunroType
import com.lojbrooks.munro.domain.model.SortDirection
import com.lojbrooks.munro.domain.model.SortedBy
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.IllegalArgumentException

class MunroQueryTest {

    private val dataSet1 = MunroQuery("src/test/assets/test_data_1.csv")
    private val dataSet2 = MunroQuery("src/test/assets/test_data_2.csv")

    @Test
    fun `GIVEN no filter WHEN query THEN return all data`() {
        assertThat(dataSet1.query().size, equalTo(10))
        assertThat(dataSet2.query().size, equalTo(21))
    }

    @Test
    fun `GIVEN filter by munro WHEN query THEN return only munros`() {
        val result1 = dataSet1.query(type = MunroType.MUNRO)
        assertThat(result1.size, equalTo(6))
        assertTrue(result1.all { it.type == MunroType.MUNRO })

        val result2 = dataSet2.query(type = MunroType.MUNRO)
        assertThat(result2.size, equalTo(14))
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

    @Test
    fun `GIVEN sort by name ascending WHEN query THEN return sorted`() {
        val result1 = dataSet1.query(sortedBy = SortedBy.Name(SortDirection.ASCENDING))
        assertThat(result1[0].name, equalTo("Ben Chonzie"))
        assertThat(result1[9].name, equalTo("Stuc a' Chroin"))

        val result2 = dataSet2.query(sortedBy = SortedBy.Name(SortDirection.ASCENDING))
        assertThat(result2[0].name, equalTo("An Caisteal"))
        assertThat(result2[20].name, equalTo("Cruach Ardrain - Stob Garbh SE Top"))
    }

    @Test
    fun `GIVEN sort by name descending WHEN query THEN return sorted`() {
        val result1 = dataSet1.query(sortedBy = SortedBy.Name(SortDirection.DESCENDING))
        assertThat(result1[0].name, equalTo("Stuc a' Chroin"))
        assertThat(result1[9].name, equalTo("Ben Chonzie"))

        val result2 = dataSet2.query(sortedBy = SortedBy.Name(SortDirection.DESCENDING))
        assertThat(result2[0].name, equalTo("Cruach Ardrain - Stob Garbh SE Top"))
        assertThat(result2[20].name, equalTo("An Caisteal"))
    }

    @Test
    fun `GIVEN sort by height ascending WHEN query THEN return sorted`() {
        val result1 = dataSet1.query(sortedBy = SortedBy.Height(SortDirection.ASCENDING))
        assertThat(result1[0].height, equalTo(923.0))
        assertThat(result1[9].height, equalTo(1174.0))

        val result2 = dataSet2.query(sortedBy = SortedBy.Height(SortDirection.ASCENDING))
        assertThat(result2[0].height, equalTo(901.7))
        assertThat(result2[20].height, equalTo(1130.0))
    }

    @Test
    fun `GIVEN sort by height descending WHEN query THEN return sorted`() {
        val result1 = dataSet1.query(sortedBy = SortedBy.Height(SortDirection.DESCENDING))
        assertThat(result1[9].height, equalTo(923.0))
        assertThat(result1[0].height, equalTo(1174.0))

        val result2 = dataSet2.query(sortedBy = SortedBy.Height(SortDirection.DESCENDING))
        assertThat(result2[20].height, equalTo(901.7))
        assertThat(result2[0].height, equalTo(1130.0))
    }

    @Test
    fun `GIVEN limit set to n WHEN query THEN return first n items`() {
        assertThat(dataSet1.query(limit = 5).size, equalTo(5))
        assertThat(dataSet2.query(limit = 6).size, equalTo(6))
    }

    @Test
    fun `GIVEN limit greater than number of munro WHEN query THEN return all data`() {
        assertThat(dataSet1.query(limit = 11).size, equalTo(10))
        assertThat(dataSet2.query(limit = 22).size, equalTo(21))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `GIVEN limit less than 1 WHEN query THEN throw`() {
        dataSet1.query(limit = 0)
    }

    @Test
    fun `GIVEN filter by min height WHEN query THEN return only munros above min height`() {
        val result1 = dataSet1.query(minHeight = 1000.0)
        assertThat(result1.size, equalTo(4))
        assertTrue(result1.all { it.height >= 1000.0 })

        val result2 = dataSet2.query(minHeight = 1000.0)
        assertThat(result2.size, equalTo(5))
        assertTrue(result2.all { it.height >= 1000.0 })
    }

    @Test
    fun `GIVEN filter by max height WHEN query THEN return only munros below max height`() {
        val result1 = dataSet1.query(maxHeight = 1000.0)
        assertThat(result1.size, equalTo(6))
        assertTrue(result1.all { it.height <= 1000.0 })

        val result2 = dataSet2.query(maxHeight = 1000.0)
        assertThat(result2.size, equalTo(16))
        assertTrue(result2.all { it.height <= 1000.0 })
    }

    @Test(expected = IllegalArgumentException::class)
    fun `GIVEN minHeight less than 0 WHEN query THEN throw`() {
        dataSet1.query(minHeight = -1.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `GIVEN maxHeight less than 0 WHEN query THEN throw`() {
        dataSet1.query(maxHeight = -1.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `GIVEN maxHeight less than minHeiht WHEN query THEN throw`() {
        dataSet1.query(minHeight = 1000.0, maxHeight = 900.0)
    }
}