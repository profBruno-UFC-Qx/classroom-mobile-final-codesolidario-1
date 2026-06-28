package com.example.givchurch.viewmodel.profile

import com.example.givchurch.domain.model.User
import com.example.givchurch.domain.repository.AuthRepository
import com.example.givchurch.domain.repository.UserRepository
import com.example.givchurch.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val authRepository: AuthRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: MainProfileViewModel

    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
    private val mockUser = User(
        id = firebaseUserId,
        firstname = "Rubens",
        lastname = "Rabelo",
        email = "rubens@email.com",
        password = "",
        imageUrl = "path/profile.jpg"
    )

    @Before
    fun setUp() {
        every { userRepository.getUserIdFlow() } returns flowOf(firebaseUserId)
        every { userRepository.getUserProfileFlow(firebaseUserId) } returns flowOf(mockUser)
    }

    @Test
    fun `init should show empty state when userIdFlow is blank`() = runTest {
        every { userRepository.getUserIdFlow() } returns flowOf("")

        viewModel = MainProfileViewModel(authRepository, userRepository)
        runCurrent()

        val state = viewModel.uiState.first()
        assertEquals("", state.userName)
        assertEquals("", state.userEmail)
    }

    @Test
    fun `init should load profile information successfully when user ID is valid`() = runTest {
        viewModel = MainProfileViewModel(authRepository, userRepository)
        runCurrent()

        val state = viewModel.uiState.first()
        assertEquals("Rubens Rabelo", state.userName)
        assertEquals("rubens@email.com", state.userEmail)
        assertEquals("path/profile.jpg", state.imageUrl)
        assertFalse(state.isLightTheme)
        assertTrue(state.isNotificationsEnabled)
    }

    @Test
    fun `toggleTheme should update state structure with matching preference flag`() = runTest {
        viewModel = MainProfileViewModel(authRepository, userRepository)
        runCurrent()

        viewModel.toggleTheme(true)
        runCurrent()

        val state = viewModel.uiState.first()
        assertTrue(state.isLightTheme)
    }

    @Test
    fun `toggleNotifications should update state structure with matching configuration flag`() = runTest {
        viewModel = MainProfileViewModel(authRepository, userRepository)
        runCurrent()

        viewModel.toggleNotifications(false)
        runCurrent()

        val state = viewModel.uiState.first()
        assertFalse(state.isNotificationsEnabled)
    }

    @Test
    fun `updateUserProfile should update state to success when repository operation returns success result`() = runTest {
        coEvery { userRepository.updateProfile(mockUser) } returns Result.success("Perfil atualizado com sucesso")

        viewModel = MainProfileViewModel(authRepository, userRepository)
        runCurrent()

        viewModel.updateUserProfile(mockUser)
        runCurrent()

        val state = viewModel.uiState.first()
        assertFalse(state.isLoading)
        assertEquals("Perfil atualizado com sucesso", state.updateMessage)
        assertNull(state.errorMessage)
    }

    @Test
    fun `updateUserProfile should update state to error when repository operation returns failure result`() = runTest {
        coEvery { userRepository.updateProfile(mockUser) } returns Result.failure(Exception("Erro de conexão"))

        viewModel = MainProfileViewModel(authRepository, userRepository)
        runCurrent()

        viewModel.updateUserProfile(mockUser)
        runCurrent()

        val state = viewModel.uiState.first()
        assertFalse(state.isLoading)
        assertNull(state.updateMessage)
        assertEquals("Erro de conexão", state.errorMessage)
    }

    @Test
    fun `clearUpdateMessages should clean status strings and retain profile context data`() = runTest {
        coEvery { userRepository.updateProfile(mockUser) } returns Result.success("Sucesso")

        viewModel = MainProfileViewModel(authRepository, userRepository)
        runCurrent()

        viewModel.updateUserProfile(mockUser)
        runCurrent()

        assertEquals("Sucesso", viewModel.uiState.first().updateMessage)

        viewModel.clearUpdateMessages()
        runCurrent()

        val state = viewModel.uiState.first()
        assertNull(state.updateMessage)
        assertNull(state.errorMessage)
    }

    @Test
    fun `onLogoutClick should perform authentication logout sequence and trigger success emitter event`() = runTest {
        coEvery { authRepository.logout() } returns Unit

        viewModel = MainProfileViewModel(authRepository, userRepository)
        runCurrent()

        var logoutTriggered = false
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            logoutTriggered = viewModel.logoutSuccess.first()
        }

        viewModel.onLogoutClick()
        runCurrent()

        assertTrue(logoutTriggered)
        coVerify(exactly = 1) { authRepository.logout() }
        job.cancel()
    }
}
