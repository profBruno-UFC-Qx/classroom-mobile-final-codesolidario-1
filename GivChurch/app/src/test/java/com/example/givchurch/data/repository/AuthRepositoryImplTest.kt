package com.example.givchurch.data.repository

import com.example.givchurch.data.mock.UserMockData
import com.example.givchurch.data.remote.firebase.service.FirebaseAuthService
import com.example.givchurch.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {

    private val authService: FirebaseAuthService = mockk(relaxed = true)
    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        authRepository = AuthRepositoryImpl(authService)
    }

    @Test
    fun `register should return success when firebase service registration succeeds`() = runTest {
        val user = UserMockData.domainUserList.last()
        val expectedUid = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
        coEvery { authService.register(any()) } returns Result.success(expectedUid)

        val result = authRepository.register(user)

        assertTrue(result.isSuccess)
        assertEquals(expectedUid, result.getOrNull())
    }

    @Test
    fun `register should return failure when firebase service registration fails`() = runTest {
        val user = UserMockData.domainUserList.last()
        val exception = Exception("Registration failed")
        coEvery { authService.register(any()) } returns Result.failure(exception)

        val result = authRepository.register(user)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `login should return success when firebase service login succeeds`() = runTest {
        val user = UserMockData.domainUserList.last()
        val expectedUid = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
        coEvery { authService.login(any()) } returns Result.success(expectedUid)

        val result = authRepository.login(user)

        assertTrue(result.isSuccess)
        assertEquals(expectedUid, result.getOrNull())
    }

    @Test
    fun `login should return failure when firebase service login fails`() = runTest {
        val user = UserMockData.domainUserList.last()
        val exception = Exception("Invalid credentials")
        coEvery { authService.login(any()) } returns Result.failure(exception)

        val result = authRepository.login(user)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `resetPassword should return success when firebase service reset succeeds`() = runTest {
        val email = "test@email.com"
        val expectedMessage = "Email sent"
        coEvery { authService.resetPassword(email) } returns Result.success(expectedMessage)

        val result = authRepository.resetPassword(email)

        assertTrue(result.isSuccess)
        assertEquals(expectedMessage, result.getOrNull())
    }

    @Test
    fun `logout should invoke logout on firebase service`() {
        authRepository.logout()

        verify(exactly = 1) { authService.logout() }
    }
}
