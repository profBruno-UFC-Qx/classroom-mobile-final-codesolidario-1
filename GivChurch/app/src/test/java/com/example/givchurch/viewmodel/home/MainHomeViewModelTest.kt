package com.example.givchurch.viewmodel.home

import com.example.givchurch.domain.model.DashboardMetrics
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.MonthlyDonation
import com.example.givchurch.domain.model.User
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.domain.repository.DashboardRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import com.example.givchurch.domain.repository.enums.SortDirection
import com.example.givchurch.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class MainHomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dashboardRepository: DashboardRepository = mockk()
    private val donationRepository: DonationRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: MainHomeViewModel

    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
    private val mockUser = User(
        id = firebaseUserId,
        firstname = "Rubens",
        lastname = "Rabelo",
        email = "rubens@email.com",
        password = "",
        imageUrl = "path/profile.jpg"
    )
    private val mockMonthlyDonations = listOf(
        MonthlyDonation(monthName = "Janeiro", totalAmount = 10)
    )
    private val mockRecentDonations = listOf(
        Donation(
            id = 1,
            imageUrl = null,
            name = "Cesta Básica",
            category = DonationCategory.FOOD,
            description = "Alimentos",
            quantity = 2,
            beneficiaryId = 10,
            createBy = firebaseUserId,
            status = DonationStatus.PENDING,
            dueDate = LocalDateTime.now().plusDays(1)
        )
    )

    @Before
    fun setUp() {
        every { userRepository.getUserIdFlow() } returns flowOf(firebaseUserId)
        every { dashboardRepository.getTotalDonations(createBy = firebaseUserId) } returns flowOf(20)
        every { dashboardRepository.getPendingDonations(createBy = firebaseUserId) } returns flowOf(5)
        every { dashboardRepository.getDeliveredDonations(createBy = firebaseUserId) } returns flowOf(15)
        every { dashboardRepository.getTotalBeneficiaries(createBy = firebaseUserId) } returns flowOf(8)
        every { dashboardRepository.getMonthlyDonations(createBy = firebaseUserId) } returns flowOf(mockMonthlyDonations)
        every { donationRepository.getAll(direction = SortDirection.DESC, limit = 5, createBy = firebaseUserId) } returns flowOf(mockRecentDonations)
        every { userRepository.getUserProfileFlow(firebaseUserId) } returns flowOf(mockUser)
    }

    @Test
    fun `init should show loading state initially when userIdFlow has not emitted or is blank`() = runTest {
        every { userRepository.getUserIdFlow() } returns flowOf("")

        viewModel = MainHomeViewModel(dashboardRepository, donationRepository, userRepository)
        runCurrent()
        advanceTimeBy(100)
        runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state is DashboardUiState.Loading)
    }

    @Test
    fun `init should load all metrics, monthly distributions and recent donations when user ID is valid`() = runTest {
        every { userRepository.getUserIdFlow() } returns flowOf(firebaseUserId)

        viewModel = MainHomeViewModel(dashboardRepository, donationRepository, userRepository)
        runCurrent()
        advanceTimeBy(100)
        runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state is DashboardUiState.Success)

        val successState = state as DashboardUiState.Success
        assertEquals("Rubens", successState.userName)
        assertEquals(mockMonthlyDonations, successState.monthlyDonations)
        assertEquals(mockRecentDonations, successState.recentDonations)

        val expectedMetrics = DashboardMetrics(
            totalDonations = 20,
            pendingDonations = 5,
            deliveredDonations = 15,
            totalBeneficiaries = 8)
        assertEquals(expectedMetrics, successState.metrics)
    }

    @Test
    fun `init should fall back to standard text name when user firstname is null or empty`() = runTest {
        every { userRepository.getUserIdFlow() } returns flowOf(firebaseUserId)
        every { dashboardRepository.getTotalDonations(createBy = firebaseUserId) } returns flowOf(20)
        every { dashboardRepository.getPendingDonations(createBy = firebaseUserId) } returns flowOf(5)
        every { dashboardRepository.getDeliveredDonations(createBy = firebaseUserId) } returns flowOf(15)
        every { dashboardRepository.getTotalBeneficiaries(createBy = firebaseUserId) } returns flowOf(8)
        every { dashboardRepository.getMonthlyDonations(createBy = firebaseUserId) } returns flowOf(mockMonthlyDonations)
        every { donationRepository.getAll(direction = SortDirection.DESC, limit = 5, createBy = firebaseUserId) } returns flowOf(mockRecentDonations)

        every { userRepository.getUserProfileFlow(firebaseUserId) } returns flowOf(null)

        viewModel = MainHomeViewModel(dashboardRepository, donationRepository, userRepository)
        runCurrent()
        advanceTimeBy(100)
        runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state is DashboardUiState.Success)
        assertEquals("Voluntário", (state as DashboardUiState.Success).userName)
    }
}
