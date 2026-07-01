package com.example.givchurch.viewmodel.beneficiary

import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.UserRepository
import com.example.givchurch.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddBeneficiaryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: BeneficiaryRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: AddBeneficiaryViewModel
    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"

    @Before
    fun setUp() {
        viewModel = AddBeneficiaryViewModel(repository, userRepository)
    }

    private fun fillMandatoryFields() {
        viewModel.onNameChanged("João Silva")
        viewModel.onPhoneChanged("88999999999")
        viewModel.onAddressChanged("Rua Central, 123")
    }

    @Test
    fun `onNameChanged should update state with new value`() {
        viewModel.onNameChanged("João Silva")
        assertEquals("João Silva", viewModel.uiState.value.name)
    }

    @Test
    fun `onPhoneChanged should update state with new value`() {
        viewModel.onPhoneChanged("88999999999")
        assertEquals("88999999999", viewModel.uiState.value.phoneNumber)
    }

    @Test
    fun `onAddressChanged should update state with new value`() {
        viewModel.onAddressChanged("Rua Central, 123")
        assertEquals("Rua Central, 123", viewModel.uiState.value.address)
    }

    @Test
    fun `onObservationsChanged should update state with new value`() {
        viewModel.onObservationsChanged("Observação válida")
        assertEquals("Observação válida", viewModel.uiState.value.observations)
    }

    @Test
    fun `loadBeneficiaryData should set mode to edit and fill state values`() {
        val beneficiary = Beneficiary(
            id = 5,
            name = "Maria Souza",
            phoneNumber = "88988888888",
            address = "Avenida Principal, 456",
            observations = "Nenhuma",
            createBy = "outro-usuario"
        )

        viewModel.loadBeneficiaryData(beneficiary)

        val state = viewModel.uiState.value
        assertEquals("Maria Souza", state.name)
        assertEquals("88988888888", state.phoneNumber)
        assertEquals("Avenida Principal, 456", state.address)
        assertEquals("Nenhuma", state.observations)
    }

    @Test
    fun `saveBeneficiary should post error message when mandatory fields are blank`() {
        viewModel.onNameChanged("")
        viewModel.onPhoneChanged("88999999999")
        viewModel.onAddressChanged("Rua Central, 123")

        viewModel.saveBeneficiary()

        assertEquals("Por favor, preencha todos os campos obrigatórios.", viewModel.uiState.value.errorMessage)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `saveBeneficiary should call repository create and emit success event when in creation mode`() = runTest {
        every { userRepository.getCurrentUserId() } returns firebaseUserId

        fillMandatoryFields()
        viewModel.onObservationsChanged("Cuidado extra")
        coEvery { repository.create(any()) } returns true

        var isSuccessEmitted = false
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            isSuccessEmitted = viewModel.saveSuccess.first()
        }

        viewModel.saveBeneficiary()

        val state = viewModel.uiState.value
        assertNull(state.errorMessage)
        assertTrue(isSuccessEmitted)
        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `saveBeneficiary should call repository update and emit success event when in edit mode`() = runTest {
        every { userRepository.getCurrentUserId() } returns firebaseUserId

        val beneficiary = Beneficiary(
            id = 10,
            name = "Antigo",
            phoneNumber = "0000",
            address = "Antigo",
            observations = "",
            createBy = firebaseUserId
        )
        viewModel.loadBeneficiaryData(beneficiary)
        fillMandatoryFields()

        coEvery { repository.update(any()) } returns true

        var isSuccessEmitted = false
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            isSuccessEmitted = viewModel.saveSuccess.first()
        }

        viewModel.saveBeneficiary()

        val state = viewModel.uiState.value
        assertNull(state.errorMessage)
        assertTrue(isSuccessEmitted)
        job.cancel()
    }

    @Test
    fun `saveBeneficiary should update state to error when creation fails because of duplicate name`() = runTest {
        every { userRepository.getCurrentUserId() } returns firebaseUserId

        fillMandatoryFields()
        coEvery { repository.create(any()) } returns false

        viewModel.saveBeneficiary()

        val state = viewModel.uiState.value
        assertEquals("Já existe um beneficiário cadastrado com este nome.", state.errorMessage)
    }

    @Test
    fun `saveBeneficiary should update state to error when update operation fails`() = runTest {
        every { userRepository.getCurrentUserId() } returns firebaseUserId

        val beneficiary = Beneficiary(
            id = 10,
            name = "Nome",
            phoneNumber = "0000",
            address = "Endereco",
            observations = "",
            createBy = firebaseUserId
        )
        viewModel.loadBeneficiaryData(beneficiary)
        coEvery { repository.update(any()) } returns false

        viewModel.saveBeneficiary()

        val state = viewModel.uiState.value
        assertEquals("Erro ao atualizar o beneficiário.", state.errorMessage)
    }

    @Test
    fun `resetForm should clean states to factory values`() {
        fillMandatoryFields()
        viewModel.onObservationsChanged("Apenas um teste")

        viewModel.resetForm()

        val state = viewModel.uiState.value
        assertEquals("", state.name)
        assertEquals("", state.phoneNumber)
        assertEquals("", state.address)
        assertEquals("", state.observations)
        assertNull(state.errorMessage)
    }
}
