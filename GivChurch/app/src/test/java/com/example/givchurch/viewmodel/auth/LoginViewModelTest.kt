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

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: AuthRepository = mockk()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun `onEmailChange should update state with new email value`() {
        val targetEmail = "test@email.com"

        viewModel.onEmailChange(targetEmail)

        assertEquals(targetEmail, viewModel.uiState.value.email)
    }

    @Test
    fun `onPasswordChange should update state with new password value`() {
        val targetPassword = "password123"

        viewModel.onPasswordChange(targetPassword)

        assertEquals(targetPassword, viewModel.uiState.value.password)
    }

    @Test
    fun `clearFields should reset state to initial configurations`() {
        viewModel.onEmailChange("test@email.com")
        viewModel.onPasswordChange("password123")

        viewModel.clearFields()

        val state = viewModel.uiState.value
        assertEquals("", state.email)
        assertEquals("", state.password)
        assertEquals("", state.message)
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
    }

    @Test
    fun `login should show warning message when email field is blank`() {
        viewModel.onEmailChange("")
        viewModel.onPasswordChange("password123")

        viewModel.login()

        val state = viewModel.uiState.value
        assertEquals("Por favor, preencha todos os campos obrigatórios.", state.message)
        assertFalse(state.isLoading)
    }

    @Test
    fun `login should show warning message when password field is blank`() {
        viewModel.onEmailChange("test@email.com")
        viewModel.onPasswordChange("")

        viewModel.login()

        val state = viewModel.uiState.value
        assertEquals("Por favor, preencha todos os campos obrigatórios.", state.message)
        assertFalse(state.isLoading)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `login should update state to success and emit event when credentials match`() = runTest {
        val email = "valid@email.com"
        val password = "validPassword"
        val successUid = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
        coEvery { repository.login(any()) } returns Result.success(successUid)
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)

        var loginEmitted: Boolean? = null
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            loginEmitted = viewModel.loginSuccess.first()
        }

        viewModel.login()

        val state = viewModel.uiState.value
        assertEquals(successUid, state.message)
        assertTrue(state.isSuccess)
        assertFalse(state.isLoading)
        assertTrue(loginEmitted == true)
        job.cancel()
    }

    @Test
    fun `login should update state to failure when authentication fails`() = runTest {
        val email = "wrong@email.com"
        val password = "wrongPassword"
        val errorMsg = "Credenciais inválidas."
        coEvery { repository.login(any()) } returns Result.failure(Exception(errorMsg))
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)

        viewModel.login()

        val state = viewModel.uiState.value
        assertEquals(errorMsg, state.message)
        assertFalse(state.isSuccess)
        assertFalse(state.isLoading)
    }
}
