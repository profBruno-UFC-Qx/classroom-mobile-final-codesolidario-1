package com.example.givchurch.viewmodel.auth

import com.example.givchurch.domain.model.User
import com.example.givchurch.domain.repository.AuthRepository
import com.example.givchurch.domain.repository.UserRepository
import com.example.givchurch.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val authRepository: AuthRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: RegisterViewModel
    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"

    @Before
    fun setUp() {
        viewModel = RegisterViewModel(authRepository, userRepository)
    }

    private fun fillMandatoryFields() {
        viewModel.onFirstnameChange("Rubens")
        viewModel.onLastnameChange("Rabelo")
        viewModel.onEmailChange("rubens@email.com")
        viewModel.onPasswordChange("password123")
    }

    @Test
    fun `onFirstnameChange should update state with new value`() {
        viewModel.onFirstnameChange("Rubens")
        assertEquals("Rubens", viewModel.uiState.value.firstname)
    }

    @Test
    fun `onLastnameChange should update state with new value`() {
        viewModel.onLastnameChange("Rabelo")
        assertEquals("Rabelo", viewModel.uiState.value.lastname)
    }

    @Test
    fun `onEmailChange should update state with new value`() {
        viewModel.onEmailChange("rubens@email.com")
        assertEquals("rubens@email.com", viewModel.uiState.value.email)
    }

    @Test
    fun `onPasswordChange should update state with new value`() {
        viewModel.onPasswordChange("password123")
        assertEquals("password123", viewModel.uiState.value.password)
    }

    @Test
    fun `resetRegisterStatus should clean states to factory values`() {
        viewModel.onFirstnameChange("Rubens")
        viewModel.resetRegisterStatus()
        assertEquals("", viewModel.uiState.value.firstname)
    }

    @Test
    fun `initEditMode should set mode to false when current user ID is blank`() {
        every { userRepository.getCurrentUserId() } returns ""

        viewModel.initEditMode()

        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `initEditMode should load existing profile values when current user ID is valid`() = runTest {
        val existingUser = User(
            id = firebaseUserId,
            firstname = "Rubens",
            lastname = "Rabelo",
            email = "rubens@email.com",
            password = "",
            imageUrl = "content://media/1"
        )
        every { userRepository.getCurrentUserId() } returns firebaseUserId
        every { userRepository.getUserProfileFlow(firebaseUserId) } returns flowOf(existingUser)

        viewModel.initEditMode()

        val state = viewModel.uiState.value
        assertEquals("Rubens", state.firstname)
        assertEquals("Rabelo", state.lastname)
        assertEquals("rubens@email.com", state.email)
        assertEquals("content://media/1", state.imageUrl)
        assertFalse(state.isLoading)
    }

    @Test
    fun `register should post warning message when mandatory text fields are blank`() {
        viewModel.onFirstnameChange("")
        viewModel.onLastnameChange("Rabelo")

        viewModel.register()

        assertEquals("Por favor, preencha todos os campos obrigatórios.", viewModel.uiState.value.message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `register should call auth service and emit success event when in creation mode`() = runTest {
        every { userRepository.getCurrentUserId() } returns ""
        viewModel.initEditMode()

        fillMandatoryFields()

        coEvery { authRepository.register(any()) } returns Result.success("Conta criada")

        var isSuccessEmitted: Boolean? = null
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            isSuccessEmitted = viewModel.registerSuccess.first()
        }

        viewModel.register()

        val state = viewModel.uiState.value
        assertEquals("Conta criada", state.message)
        assertTrue(state.isSuccess)
        assertFalse(state.isLoading)
        assertTrue(isSuccessEmitted == true)
        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `register should call user repository profile update when in editing mode`() = runTest {
        val existingUser = User(
            id = firebaseUserId,
            firstname = "Rubens",
            lastname = "Rabelo",
            email = "rubens@email.com",
            password = "",
            imageUrl = "content://media/1"
        )
        every { userRepository.getCurrentUserId() } returns firebaseUserId
        every { userRepository.getUserProfileFlow(firebaseUserId) } returns flowOf(existingUser)
        viewModel.initEditMode()

        fillMandatoryFields()

        coEvery { userRepository.updateProfile(any()) } returns Result.success("Perfil atualizado")

        var isSuccessEmitted: Boolean? = null
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            isSuccessEmitted = viewModel.registerSuccess.first()
        }

        viewModel.register()

        val state = viewModel.uiState.value
        assertEquals("Perfil atualizado", state.message)
        assertTrue(state.isSuccess)
        assertFalse(state.isLoading)
        assertTrue(isSuccessEmitted == true)
        job.cancel()
    }

    @Test
    fun `register should update state to failure when operations throw exception`() = runTest {
        every { userRepository.getCurrentUserId() } returns ""
        viewModel.initEditMode()

        fillMandatoryFields()

        coEvery { authRepository.register(any()) } returns Result.failure(Exception("Erro de conexão"))

        viewModel.register()

        val state = viewModel.uiState.value
        assertEquals("Erro de conexão", state.message)
        assertFalse(state.isSuccess)
        assertFalse(state.isLoading)
    }
}
