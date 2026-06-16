package com.example.givchurch.viewmodel.beneficiary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.data.local.model.Beneficiary
import com.example.givchurch.data.repository.BeneficiaryRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddBeneficiaryViewModel(
    private val repository: BeneficiaryRepository = BeneficiaryRepository()
) : ViewModel() {

    var name by mutableStateOf("")
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var address by mutableStateOf("")
        private set
    var observations by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val _saveSuccess = MutableSharedFlow<Boolean>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    fun onNameChanged(newValue: String) { name = newValue }
    fun onPhoneChanged(newValue: String) { phoneNumber = newValue }
    fun onAddressChanged(newValue: String) { address = newValue }
    fun onObservationsChanged(newValue: String) { observations = newValue }

    fun saveBeneficiary() {
        if (name.isBlank() || phoneNumber.isBlank() || address.isBlank()) {
            errorMessage = "Por favor, preencha todos os campos obrigatórios."
            return
        }

        errorMessage = null

        viewModelScope.launch {
            val newBen = Beneficiary(
                name = name,
                phoneNumber = phoneNumber,
                address = address,
                observations = observations,
                createBy = 1 // TODO: alterar isso para pegar o id do usuário logado
            )

            val success = repository.create(newBen)

            if (success) {
                _saveSuccess.emit(true)
            } else {
                errorMessage = "Já existe uma beneficiário cadastrada com este nome."
            }
        }
    }
}
