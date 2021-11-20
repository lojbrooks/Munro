package com.lojbrooks.munro

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MunroQueryTest {

    @Test
    fun `GIVEN no filter WHEN search THEN return all data`() {
        assertThat(MunroQuery("src/test/assets/test_data_1.csv").query().size, equalTo(10))
        assertThat(MunroQuery("src/test/assets/test_data_2.csv").query().size, equalTo(20))
    }
}