package com.example.givchurch.viewmodel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainHistoryViewModel(
    private val repository: DonationRepository,
    private val beneficiaryRepository: BeneficiaryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val userId = userRepository.getCurrentUserId()
            repository.getAll(createBy = userId).collect { items ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        historyItems = items
                    )
                }
            }
        }
    }

    fun loadBeneficiaryName(id: Int, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val userId = userRepository.getCurrentUserId()
            val beneficiary = beneficiaryRepository.getById(id, createBy = userId)
            onResult(beneficiary?.name ?: "Beneficiário Desconhecido")
        }
    }
}
