package com.example.givchurch.data.repository

import com.example.givchurch.data.mock.VerseMockData
import com.example.givchurch.data.remote.api.service.BibleWebService
import com.example.givchurch.domain.repository.BibleRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BibleRepositoryImplTest {

    private val webService: BibleWebService = mockk()
    private lateinit var bibleRepository: BibleRepository

    @Before
    fun setUp() {
        bibleRepository = BibleRepositoryImpl(webService)
    }

    @Test
    fun `getVerseOfTheDay should return success result with mapped domain verse when web service succeeds`() = runTest {
        val response = VerseMockData.response
        val expectedDomain = VerseMockData.domain
        coEvery { webService.fetchRandomVerse() } returns response

        val result = bibleRepository.getVerseOfTheDay()

        assertTrue(result.isSuccess)
        assertEquals(expectedDomain, result.getOrNull())
    }

    @Test
    fun `getVerseOfTheDay should return failure result with exception when web service throws an error`() = runTest {
        val exception = Exception("API Error")
        coEvery { webService.fetchRandomVerse() } throws exception

        val result = bibleRepository.getVerseOfTheDay()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
