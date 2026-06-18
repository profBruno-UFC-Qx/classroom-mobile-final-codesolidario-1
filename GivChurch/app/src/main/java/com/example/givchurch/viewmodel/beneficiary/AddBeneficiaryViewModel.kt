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
            val newBen = Beneficiary(
                name = currentState.name,
                phoneNumber = currentState.phoneNumber,
                address = currentState.address,
                observations = currentState.observations,
                createBy = userRepository.getCurrentUserId()
            )

            val success = repository.create(newBen)

            if (success) {
                _saveSuccess.emit(true)
            } else {
                _uiState.update { it.copy(errorMessage = "Já existe uma beneficiário cadastrada com este nome.") }
            }
        }
    }
}
