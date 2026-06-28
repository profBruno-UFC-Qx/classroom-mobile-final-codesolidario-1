package com.example.givchurch.viewmodel.beneficiary

import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.repository.BeneficiaryRepository
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

@OptIn(ExperimentalCoroutinesApi::class)
class MainBeneficiaryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: BeneficiaryRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: MainBeneficiaryViewModel
    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
    private val mockBeneficiaries = listOf(
        Beneficiary(1, "Ana Silva", "8899", "Rua A", "", "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"),
        Beneficiary(2, "Carlos Oliveira", "8898", "Rua B", "", "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH")
    )

    @Before
    fun setUp() {
        every { userRepository.getUserIdFlow() } returns flowOf(firebaseUserId)
        every { userRepository.getCurrentUserId() } returns firebaseUserId
        every { repository.getAll(firebaseUserId) } returns flowOf(mockBeneficiaries)
        every { repository.getByName("Ana", firebaseUserId) } returns flowOf(mockBeneficiaries)
    }

    @Test
    fun `onSearchQueryChanged should update state with new value`() = runTest {
        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()

        viewModel.onSearchQueryChanged("Ana")
        advanceTimeBy(300)
        runCurrent()

        assertEquals("Ana", viewModel.uiState.value.searchQuery)
    }

    @Test
    fun `init should load all beneficiaries when user ID is valid and search query is blank`() = runTest {
        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        val state = viewModel.uiState.value
        assertEquals(mockBeneficiaries, state.beneficiariesList)
        assertEquals("", state.searchQuery)
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertNull(state.errorMessage)
    }

    @Test
    fun `init should load empty list when user ID is blank`() = runTest {
        every { userRepository.getUserIdFlow() } returns flowOf("")

        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state.beneficiariesList.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSearchQueryChanged should trigger search after debounce timeout`() = runTest {
        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        viewModel.onSearchQueryChanged("Ana")
        runCurrent()

        advanceTimeBy(300)
        runCurrent()

        val state = viewModel.uiState.value
        assertEquals("Ana", state.searchQuery)
        assertEquals(mockBeneficiaries, state.beneficiariesList)
    }

    @Test
    fun `updateBeneficiary should update state to success when repository update returns true`() = runTest {
        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        val beneficiaryToUpdate = mockBeneficiaries.first()
        coEvery { repository.update(beneficiaryToUpdate) } returns true

        viewModel.updateBeneficiary(beneficiaryToUpdate)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.isSuccess)
        assertNull(state.errorMessage)
    }

    @Test
    fun `updateBeneficiary should update state to error when repository update returns false`() = runTest {
        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        val beneficiaryToUpdate = mockBeneficiaries.first()
        coEvery { repository.update(beneficiaryToUpdate) } returns false

        viewModel.updateBeneficiary(beneficiaryToUpdate)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertEquals("Erro ao atualizar atendido.", state.errorMessage)
    }

    @Test
    fun `deleteBeneficiary should update state to success when repository delete returns true`() = runTest {
        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        coEvery { repository.delete(1, firebaseUserId) } returns true

        viewModel.deleteBeneficiary(1)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.isSuccess)
        assertNull(state.errorMessage)
    }

    @Test
    fun `deleteBeneficiary should update state to error when repository delete returns false`() = runTest {
        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        coEvery { repository.delete(1, firebaseUserId) } returns false

        viewModel.deleteBeneficiary(1)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertEquals("Erro ao excluir atendido.", state.errorMessage)
    }

    @Test
    fun `deleteBeneficiary should post error message when current user ID is blank`() = runTest {
        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        every { userRepository.getCurrentUserId() } returns ""

        viewModel.deleteBeneficiary(1)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertEquals("Usuário não autenticado.", state.errorMessage)
    }

    @Test
    fun `clearOperationStatus should clean operation indicators back to factory state`() = runTest {
        viewModel = MainBeneficiaryViewModel(repository, userRepository)
        runCurrent()
        advanceTimeBy(300)
        runCurrent()

        coEvery { repository.delete(1, firebaseUserId) } returns false
        viewModel.deleteBeneficiary(1)
        runCurrent()

        assertEquals("Erro ao excluir atendido.", viewModel.uiState.value.errorMessage)

        viewModel.clearOperationStatus()
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertNull(state.errorMessage)
    }
}
