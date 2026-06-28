package com.example.givchurch.data.repository

import com.example.givchurch.data.mock.UserMockData
import com.example.givchurch.data.remote.firebase.service.FirebaseUserService
import com.example.givchurch.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private val userService: FirebaseUserService = mockk()
    private lateinit var userRepository: UserRepository
    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"

    @Before
    fun setUp() {
        userRepository = UserRepositoryImpl(userService)
    }

    @Test
    fun `getCurrentUserId should return raw string identifier from service`() {
        every { userService.getCurrentUserId() } returns firebaseUserId

        val result = userRepository.getCurrentUserId()

        assertEquals(firebaseUserId, result)
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `getUserIdFlow should emit current authenticated user session id`() = runTest {
        every { userService.getUserIdFlow() } returns flowOf(firebaseUserId)

        val result = userRepository.getUserIdFlow().first()

        assertEquals(firebaseUserId, result)
        verify(exactly = 1) {
            val unused = userService.getUserIdFlow()
        }
    }

    @Test
    fun `updateProfile should forward mapped data structure and return success state`() = runTest {
        val domainUser = UserMockData.domainUserList.first()
        coEvery { userService.updateProfile(any()) } returns Result.success(firebaseUserId)

        val result = userRepository.updateProfile(domainUser)

        assertTrue(result.isSuccess)
        assertEquals(firebaseUserId, result.getOrNull())
    }

    @Test
    fun `updateProfile should return failure encapsulating service exception`() = runTest {
        val domainUser = UserMockData.domainUserList.first()
        val exception = Exception("Firestore sync failure")
        coEvery { userService.updateProfile(any()) } returns Result.failure(exception)

        val result = userRepository.updateProfile(domainUser)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `getUserProfileFlow should map existing collection document records into user entity`() = runTest {
        val localUser = UserMockData.localUserList.first()
        every { userService.getUserProfileFlow(firebaseUserId) } returns flowOf(localUser)

        val result = userRepository.getUserProfileFlow(firebaseUserId).first()

        assertEquals(localUser.id, result?.id)
        assertEquals(localUser.firstname, result?.firstname)
        verify(exactly = 1) {
            val unused = userService.getUserProfileFlow(firebaseUserId)
        }
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `getUserProfileFlow should fallback into localized default when database record is missing`() = runTest {
        every { userService.getUserProfileFlow(firebaseUserId) } returns flowOf(null)

        val result = userRepository.getUserProfileFlow(firebaseUserId).first()

        assertEquals(firebaseUserId, result?.id)
        assertEquals("Voluntário", result?.firstname)
        assertEquals("", result?.lastname)
        verify(exactly = 1) {
            val unused = userService.getUserProfileFlow(firebaseUserId)
        }
    }
}
