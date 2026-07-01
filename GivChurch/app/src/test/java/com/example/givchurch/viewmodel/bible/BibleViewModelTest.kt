package com.example.givchurch.viewmodel.bible

import com.example.givchurch.domain.model.Verse
import com.example.givchurch.domain.repository.BibleRepository
import com.example.givchurch.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BibleViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val bibleRepository: BibleRepository = mockk()
    private lateinit var viewModel: BibleViewModel

    private val mockVerse = Verse(
        reference = "Salmos 23:1",
        text = "O Senhor é o meu pastor, nada me faltará."
    )

    @Test
    fun `init should load verse of the day successfully`() = runTest {
        coEvery { bibleRepository.getVerseOfTheDay() } returns Result.success(mockVerse)

        viewModel = BibleViewModel(bibleRepository)
        runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state is BibleUiState.Success)
        assertEquals(mockVerse, (state as BibleUiState.Success).verse)
    }

    @Test
    fun `init should update state to error with fallback verse when repository fails`() = runTest {
        coEvery { bibleRepository.getVerseOfTheDay() } returns Result.failure(Exception("Network error"))

        viewModel = BibleViewModel(bibleRepository)
        runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state is BibleUiState.Error)
        assertEquals(
            "\"O Senhor é o meu pastor, nada me faltará.\" Salmos 23:1",
            (state as BibleUiState.Error).fallbackVerse
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadVerse should set loading state before repository response`() = runTest {
        coEvery { bibleRepository.getVerseOfTheDay() } coAnswers {
            delay(1000)
            Result.success(mockVerse)
        }

        viewModel = BibleViewModel(bibleRepository)
        runCurrent()

        viewModel.loadVerse()

        val state = viewModel.uiState.value
        assertTrue(state is BibleUiState.Loading)
    }
}
