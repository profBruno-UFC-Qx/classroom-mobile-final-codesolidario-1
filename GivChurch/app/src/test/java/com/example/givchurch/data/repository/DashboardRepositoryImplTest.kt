package com.example.givchurch.data.repository

import com.example.givchurch.data.local.dao.DashboardDao
import com.example.givchurch.data.mock.DashboardMockData
import com.example.givchurch.domain.repository.DashboardRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class DashboardRepositoryImplTest {

    private val dashboardDao: DashboardDao = mockk()
    private lateinit var dashboardRepository: DashboardRepository
    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"

    @Before
    fun setUp() {
        dashboardRepository = DashboardRepositoryImpl(dashboardDao)
    }

    @Test
    fun `getTotalDonations should emit numeric value from dao`() = runTest {
        every { dashboardDao.getTotalDonations(firebaseUserId) } returns flowOf(100)

        val result = dashboardRepository.getTotalDonations(firebaseUserId).first()

        assertEquals(100, result)
    }

    @Test
    fun `getPendingDonations should emit numeric value from dao`() = runTest {
        every { dashboardDao.getPendingDonations(firebaseUserId) } returns flowOf(25)

        val result = dashboardRepository.getPendingDonations(firebaseUserId).first()

        assertEquals(25, result)
    }

    @Test
    fun `getDeliveredDonations should emit numeric value from dao`() = runTest {
        every { dashboardDao.getDeliveredDonations(firebaseUserId) } returns flowOf(75)

        val result = dashboardRepository.getDeliveredDonations(firebaseUserId).first()

        assertEquals(75, result)
    }

    @Test
    fun `getTotalBeneficiaries should emit numeric value from dao`() = runTest {
        every { dashboardDao.getTotalBeneficiaries(firebaseUserId) } returns flowOf(12)

        val result = dashboardRepository.getTotalBeneficiaries(firebaseUserId).first()

        assertEquals(12, result)
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `getMonthlyDonations should map database entities into past five months structure`() = runTest {
        val currentYearStr = LocalDateTime.now().year.toString()
        val expectedMocks = DashboardMockData.domainMonthlyDonations

        every { dashboardDao.getDonationsForYear(currentYearStr, firebaseUserId) } returns flowOf(emptyList())

        val result = dashboardRepository.getMonthlyDonations(firebaseUserId).first()

        assertEquals(5, result.size)

        verify(exactly = 1) {
            val unused = dashboardDao.getDonationsForYear(currentYearStr, firebaseUserId)
        }
    }
}
