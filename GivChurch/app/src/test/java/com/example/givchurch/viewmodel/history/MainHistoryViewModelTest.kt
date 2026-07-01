package com.example.givchurch.viewmodel.history

import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import com.example.givchurch.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class MainHistoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: DonationRepository = mockk()
    private val beneficiaryRepository: BeneficiaryRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: MainHistoryViewModel

    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
    private val mockDonations = listOf(
        Donation(
            id = 1,
            imageUrl = null,
            name = "Cesta Básica",
            category = DonationCategory.FOOD,
            description = "Alimentos",
            quantity = 2,
            beneficiaryId = 10,
            createBy = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH",
            status = DonationStatus.DELIVERED,
            dueDate = LocalDateTime.now().plusDays(1)
        )
    )
    private val mockBeneficiary = Beneficiary(10, "Ana Silva", "8899", "Rua A", "", "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH")

    @Before
    fun setUp() {
        every { userRepository.getUserIdFlow() } returns flowOf(firebaseUserId)
        every { userRepository.getCurrentUserId() } returns firebaseUserId
        every { repository.getAll(createBy = firebaseUserId) } returns flowOf(mockDonations)
        coEvery { beneficiaryRepository.getById(10, firebaseUserId) } returns mockBeneficiary
    }

    @Test
    fun `init should load donation history items successfully when user ID is valid`() = runTest {
        viewModel = MainHistoryViewModel(repository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(mockDonations, state.historyItems)
    }

    @Test
    fun `init should emit empty list when user ID is blank`() = runTest {
        every { userRepository.getUserIdFlow() } returns flowOf("")

        viewModel = MainHistoryViewModel(repository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.historyItems.isEmpty())
    }

    @Test
    fun `loadBeneficiaryName should return mapped name via callback receiver`() = runTest {
        viewModel = MainHistoryViewModel(repository, beneficiaryRepository, userRepository)
        runCurrent()

        var resultName = ""
        viewModel.loadBeneficiaryName(10) { name ->
            resultName = name
        }
        runCurrent()

        assertEquals("Ana Silva", resultName)
    }

    @Test
    fun `loadBeneficiaryName should return fallback string via callback receiver when target is null`() = runTest {
        coEvery { beneficiaryRepository.getById(10, firebaseUserId) } returns null

        viewModel = MainHistoryViewModel(repository, beneficiaryRepository, userRepository)
        runCurrent()

        var resultName = ""
        viewModel.loadBeneficiaryName(10) { name ->
            resultName = name
        }
        runCurrent()

        assertEquals("Beneficiário Desconhecido", resultName)
    }
}
