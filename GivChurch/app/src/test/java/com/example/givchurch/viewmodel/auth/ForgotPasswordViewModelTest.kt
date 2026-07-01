package com.example.givchurch.viewmodel.auth

import com.example.givchurch.domain.repository.AuthRepository
import com.example.givchurch.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ForgotPasswordViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: AuthRepository = mockk()
    private lateinit var viewModel: ForgotPasswordViewModel

    @Before
    fun setUp() {
        viewModel = ForgotPasswordViewModel(repository)
    }

    @Test
    fun `onEmailChange should update state with new email value`() {
        val targetEmail = "user@email.com"

        viewModel.onEmailChange(targetEmail)

        assertEquals(targetEmail, viewModel.uiState.value.email)
    }

    @Test
    fun `clearFields should reset state to initial empty configurations`() {
        viewModel.onEmailChange("test@email.com")

        viewModel.clearFields()

        assertEquals("", viewModel.uiState.value.email)
        assertEquals("", viewModel.uiState.value.message)
        assertFalse(viewModel.uiState.value.isLoading)
        assertFalse(viewModel.uiState.value.isSuccess)
    }

    @Test
    fun `resetPassword should update state with warning message when email field is blank`() {
        viewModel.onEmailChange("")

        viewModel.resetPassword()

        val state = viewModel.uiState.value
        assertEquals("Por favor, preencha o campo de e-mail.", state.message)
        assertFalse(state.isLoading)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `resetPassword should update state to success and emit event when repository succeeds`() = runTest {
        val targetEmail = "valid@email.com"
        val successMsg = "E-mail de redefinição enviado"
        coEvery { repository.resetPassword(targetEmail) } returns Result.success(successMsg)
        viewModel.onEmailChange(targetEmail)

        var emittedEvent: Boolean? = null
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            emittedEvent = viewModel.resetSuccess.first()
        }

        viewModel.resetPassword()

        val state = viewModel.uiState.value
        assertEquals(successMsg, state.message)
        assertTrue(state.isSuccess)
        assertFalse(state.isLoading)
        assertTrue(emittedEvent == true)
        job.cancel()
    }

    @Test
    fun `resetPassword should update state to failure when repository fails to send request`() = runTest {
        val targetEmail = "valid@email.com"
        val errorMsg = "Erro na rede do servidor"
        coEvery { repository.resetPassword(targetEmail) } returns Result.failure(Exception(errorMsg))
        viewModel.onEmailChange(targetEmail)

        viewModel.resetPassword()

        val state = viewModel.uiState.value
        assertEquals(errorMsg, state.message)
        assertFalse(state.isSuccess)
        assertFalse(state.isLoading)
    }
}
