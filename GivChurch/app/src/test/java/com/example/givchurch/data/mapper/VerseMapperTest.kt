package com.example.givchurch.data.mapper

import com.example.givchurch.data.mock.VerseMockData
import org.junit.Assert.assertEquals
import org.junit.Test

class VerseMapperTest {

    @Test
    fun `should map verse response to domain model with correct fields`() {
        val response = VerseMockData.response

        val domain = response.toDomain()

        assertEquals(response.reference, domain.reference)
        assertEquals(response.text, domain.text)
    }
}
