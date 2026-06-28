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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
class DonationDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val donationRepository: DonationRepository = mockk()
    private val beneficiaryRepository: BeneficiaryRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: DonationDetailViewModel

    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
    private val mockDonation = Donation(
        id = 1,
        imageUrl = "path/img.jpg",
        name = "Cesta Básica",
        category = DonationCategory.FOOD,
        description = "Alimentos diversos",
        quantity = 2,
        beneficiaryId = 10,
        createBy = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH",
        status = DonationStatus.PENDING,
        dueDate = LocalDateTime.now().plusDays(1)
    )
    private val mockBeneficiary = Beneficiary(10, "Ana Silva", "8899", "Rua A", "", "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH")

    @Before
    fun setUp() {
        every { userRepository.getCurrentUserId() } returns firebaseUserId
    }

    @Test
    fun `activeDonationFlow should emit error state when donationId is null`() = runTest {
        viewModel = DonationDetailViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        val state = viewModel.activeDonationFlow.first()
        assertEquals("Doação inválida.", state.errorMessage)
    }

    @Test
    fun `activeDonationFlow should emit loaded data when donation and beneficiary are found`() = runTest {
        every { donationRepository.getById(1, firebaseUserId) } returns flowOf(mockDonation)
        coEvery { beneficiaryRepository.getById(10, firebaseUserId) } returns mockBeneficiary

        viewModel = DonationDetailViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.loadDonationDetails(1)
        runCurrent()

        val state = viewModel.activeDonationFlow.first()
        assertEquals(mockDonation, state.donation)
        assertEquals("Ana Silva", state.beneficiaryName)
        assertNull(state.errorMessage)
    }

    @Test
    fun `activeDonationFlow should emit error when donation is not found`() = runTest {
        every { donationRepository.getById(1, firebaseUserId) } returns flowOf(null)

        viewModel = DonationDetailViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.loadDonationDetails(1)
        runCurrent()

        val state = viewModel.activeDonationFlow.first()
        assertNull(state.donation)
        assertEquals("Doação não encontrada.", state.errorMessage)
    }

    @Test
    fun `updateDonationStatus should update repository and retain loading state behavior on failure`() = runTest {
        every { donationRepository.getById(1, firebaseUserId) } returns flowOf(mockDonation)
        coEvery { beneficiaryRepository.getById(10, firebaseUserId) } returns mockBeneficiary
        coEvery { donationRepository.update(any()) } returns false

        viewModel = DonationDetailViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.loadDonationDetails(1)
        // Coleta o estado reativo para ativar as transformações internas e popular o fluxo
        viewModel.activeDonationFlow.first()
        runCurrent()

        viewModel.updateDonationStatus(DonationStatus.DELIVERED)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("Falha ao atualizar o status.", state.errorMessage)
    }

    @Test
    fun `updateDonationStatus should clear loading state on success response`() = runTest {
        every { donationRepository.getById(1, firebaseUserId) } returns flowOf(mockDonation)
        coEvery { beneficiaryRepository.getById(10, firebaseUserId) } returns mockBeneficiary
        coEvery { donationRepository.update(any()) } returns true

        viewModel = DonationDetailViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.loadDonationDetails(1)
        viewModel.activeDonationFlow.first()
        runCurrent()

        viewModel.updateDonationStatus(DonationStatus.DELIVERED)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
    }

    @Test
    fun `deleteDonation should update state to success when repository operation returns true`() = runTest {
        viewModel = DonationDetailViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.loadDonationDetails(1)
        runCurrent()

        coEvery { donationRepository.delete(1, firebaseUserId) } returns true

        viewModel.deleteDonation()
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.isDeleteSuccess)
        assertNull(state.errorMessage)
    }

    @Test
    fun `deleteDonation should update state to error when repository operation returns false`() = runTest {
        viewModel = DonationDetailViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.loadDonationDetails(1)
        runCurrent()

        coEvery { donationRepository.delete(1, firebaseUserId) } returns false

        viewModel.deleteDonation()
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isDeleteSuccess)
        assertEquals("Erro ao excluir doação.", state.errorMessage)
    }

    @Test
    fun `clearErrorMessage should remove active error string from UI state structure`() = runTest {
        viewModel = DonationDetailViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.loadDonationDetails(1)
        runCurrent()

        coEvery { donationRepository.delete(1, firebaseUserId) } returns false
        viewModel.deleteDonation()
        runCurrent()

        assertEquals("Erro ao excluir doação.", viewModel.uiState.value.errorMessage)

        viewModel.clearErrorMessage()
        runCurrent()

        assertNull(viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `resetViewModel should clean data values back to standard initialization state`() = runTest {
        viewModel = DonationDetailViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.loadDonationDetails(1)
        runCurrent()

        coEvery { donationRepository.delete(1, firebaseUserId) } returns false
        viewModel.deleteDonation()
        runCurrent()

        viewModel.resetViewModel()
        runCurrent()

        val state = viewModel.uiState.value
        assertNull(state.donation)
        assertFalse(state.isDeleteSuccess)
        assertNull(state.errorMessage)
    }
}
