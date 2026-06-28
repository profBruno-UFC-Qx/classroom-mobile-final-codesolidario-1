package com.example.givchurch.viewmodel.donation

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
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class MainDonationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val donationRepository: DonationRepository = mockk()
    private val beneficiaryRepository: BeneficiaryRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: MainDonationViewModel

    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
    private val mockDonation = Donation(
        id = 1,
        imageUrl = null,
        name = "Cesta Básica",
        category = DonationCategory.FOOD,
        description = "",
        quantity = 2,
        beneficiaryId = 10,
        createBy = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH",
        status = DonationStatus.PENDING,
        dueDate = LocalDateTime.now().plusDays(1)
    )
    private val mockBeneficiary = Beneficiary(10, "Ana Silva", "8899", "Rua A", "", "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH")

    @Before
    fun setUp() {
        every { userRepository.getUserIdFlow() } returns flowOf(firebaseUserId)
        every { userRepository.getCurrentUserId() } returns firebaseUserId
        every { donationRepository.searchAndFilter(any(), any(), firebaseUserId) } returns flowOf(listOf(mockDonation))
        coEvery { beneficiaryRepository.getById(10, firebaseUserId) } returns mockBeneficiary
    }

    @Test
    fun `onSearchQueryChanged should update state with new value`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onSearchQueryChanged("Cesta")
        advanceTimeBy(300)
        runCurrent()

        assertEquals("Cesta", viewModel.uiState.value.searchQuery)
    }

    @Test
    fun `onCategorySelected should update state with new value`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        viewModel.onCategorySelected(DonationCategory.CLOTHING)
        runCurrent()

        assertEquals(DonationCategory.CLOTHING, viewModel.uiState.value.selectedCategory)
    }

    @Test
    fun `init should load and map donations list with beneficiary name as description when user ID is valid`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        val state = viewModel.uiState.value
        assertEquals(1, state.donationsList.size)
        assertEquals("Ana Silva", state.donationsList.first().description)
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertNull(state.errorMessage)
    }

    @Test
    fun `init should load empty list when user ID is blank`() = runTest {
        every { userRepository.getUserIdFlow() } returns flowOf("")

        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state.donationsList.isEmpty())
    }

    @Test
    fun `loadBeneficiaryName should return mapped beneficiary name via callback handler`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        var resultName = ""
        viewModel.loadBeneficiaryName(10) { name ->
            resultName = name
        }
        runCurrent()

        assertEquals("Ana Silva", resultName)
    }

    @Test
    fun `updateDonation should update state to success when repository update returns true`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        coEvery { donationRepository.update(mockDonation) } returns true

        viewModel.updateDonation(mockDonation)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.isSuccess)
        assertNull(state.errorMessage)
    }

    @Test
    fun `updateDonation should update state to error when repository update returns false`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        coEvery { donationRepository.update(mockDonation) } returns false

        viewModel.updateDonation(mockDonation)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertEquals("Erro ao atualizar doação.", state.errorMessage)
    }

    @Test
    fun `deleteDonation should update state to success when repository delete returns true`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        coEvery { donationRepository.delete(1, firebaseUserId) } returns true

        viewModel.deleteDonation(1)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.isSuccess)
        assertNull(state.errorMessage)
    }

    @Test
    fun `deleteDonation should update state to error when repository delete returns false`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        coEvery { donationRepository.delete(1, firebaseUserId) } returns false

        viewModel.deleteDonation(1)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertEquals("Erro ao excluir doação.", state.errorMessage)
    }

    @Test
    fun `deleteDonation should post error message when current user ID is blank`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        every { userRepository.getCurrentUserId() } returns ""

        viewModel.deleteDonation(1)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertEquals("Usuário não autenticado.", state.errorMessage)
    }

    @Test
    fun `clearOperationStatus should clean operation indicators back to factory state`() = runTest {
        viewModel = MainDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        coEvery { donationRepository.delete(1, firebaseUserId) } returns false
        viewModel.deleteDonation(1)
        runCurrent()

        assertEquals("Erro ao excluir doação.", viewModel.uiState.value.errorMessage)

        viewModel.clearOperationStatus()
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertNull(state.errorMessage)
    }
}
