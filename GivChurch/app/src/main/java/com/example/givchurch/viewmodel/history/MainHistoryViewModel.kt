package com.example.givchurch.viewmodel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainHistoryViewModel(
    private val repository: DonationRepository,
    private val beneficiaryRepository: BeneficiaryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val uiState: StateFlow<HistoryUiState> = userRepository.getUserIdFlow()
        .flatMapLatest { userId ->
            if (userId.isBlank()) {
                flowOf(emptyList())
            } else {
                repository.getAll(createBy = userId)
            }
        }.map { items ->
            HistoryUiState(
                isLoading = false,
                historyItems = items
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = HistoryUiState(isLoading = true)
        )

    fun loadBeneficiaryName(id: Int, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val userId = userRepository.getCurrentUserId()
            val beneficiary = beneficiaryRepository.getById(id, createBy = userId)
            onResult(beneficiary?.name ?: "Beneficiário Desconhecido")
        }
    }
}
