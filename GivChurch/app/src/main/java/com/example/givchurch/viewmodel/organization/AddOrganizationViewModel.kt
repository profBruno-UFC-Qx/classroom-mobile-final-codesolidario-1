package com.example.givchurch.viewmodel.organization

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.data.model.Organization
import com.example.givchurch.data.repository.OrganizationRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddOrganizationViewModel(
    private val repository: OrganizationRepository = OrganizationRepository()
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

    fun saveOrganization() {
        if (name.isBlank() || phoneNumber.isBlank() || address.isBlank()) {
            errorMessage = "Por favor, preencha todos os campos obrigatórios."
            return
        }

        errorMessage = null

        viewModelScope.launch {
            val newOrg = Organization(
                name = name,
                phoneNumber = phoneNumber,
                address = address,
                observations = observations,
                createBy = 1 // TODO: alterar isso para pegar o id do usuário logado
            )

            val success = repository.create(newOrg)

            if (success) {
                _saveSuccess.emit(true)
            } else {
                errorMessage = "Já existe uma organização cadastrada com este nome."
            }
        }
    }
}
