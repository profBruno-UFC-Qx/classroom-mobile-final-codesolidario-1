package com.example.givchurch.viewmodel.beneficiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddBeneficiaryViewModel(
    private val repository: BeneficiaryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBeneficiaryUiState())
    val uiState: StateFlow<AddBeneficiaryUiState> = _uiState.asStateFlow()

    private val _saveSuccess = MutableSharedFlow<Boolean>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    private var currentBeneficiaryId: Int? = null
    private var isEditMode: Boolean = false

    fun loadBeneficiaryData(beneficiary: Beneficiary) {
        currentBeneficiaryId = beneficiary.id
        isEditMode = true
        _uiState.update {
            it.copy(
                name = beneficiary.name,
                phoneNumber = beneficiary.phoneNumber,
                address = beneficiary.address,
                observations = beneficiary.observations
            )
        }
    }

    fun onNameChanged(newValue: String) {
        _uiState.update { it.copy(name = newValue) }
    }

    fun onPhoneChanged(newValue: String) {
        _uiState.update { it.copy(phoneNumber = newValue) }
    }

    fun onAddressChanged(newValue: String) {
        _uiState.update { it.copy(address = newValue) }
    }

    fun onObservationsChanged(newValue: String) {
        _uiState.update { it.copy(observations = newValue) }
    }

    fun saveBeneficiary() {
        val currentState = _uiState.value

        if (currentState.name.isBlank() || currentState.phoneNumber.isBlank() || currentState.address.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Por favor, preencha todos os campos obrigatórios.") }
            return
        }

        _uiState.update { it.copy(errorMessage = null) }

        viewModelScope.launch {
            val currentUserId = userRepository.getCurrentUserId()

            val beneficiaryModel = Beneficiary(
                id = currentBeneficiaryId?: 0,
                name = currentState.name,
                phoneNumber = currentState.phoneNumber,
                address = currentState.address,
                observations = currentState.observations,
                createBy = currentUserId
            )

            val success = if (isEditMode) {
                repository.update(beneficiaryModel)
            } else {
                repository.create(beneficiaryModel)
            }

            if (success) {
                _saveSuccess.emit(true)
            } else {
                val message = if (isEditMode) "Erro ao atualizar o beneficiário." else "Já existe um beneficiário cadastrado com este nome."
                _uiState.update { it.copy(errorMessage = message) }
            }
        }
    }
}
