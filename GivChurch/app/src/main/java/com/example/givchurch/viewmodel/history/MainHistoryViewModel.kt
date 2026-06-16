package com.example.givchurch.viewmodel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.data.local.model.Donation
import com.example.givchurch.data.repository.DonationRepository
import com.example.givchurch.data.repository.BeneficiaryRepository // 👈 Importado aqui
import com.example.givchurch.data.repository.enums.SortDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HistoryUiState(
    val isLoading: Boolean = false,
    val historyItems: List<Donation> = emptyList(),
    val isLastPage: Boolean = false
)

class HistoryViewModel(
    private val repository: DonationRepository = DonationRepository(),
    private val beneficiaryRepository: BeneficiaryRepository = BeneficiaryRepository() // 👈 Injetado aqui
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private val pageSize = 5

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (_uiState.value.isLoading || _uiState.value.isLastPage) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val newItems = repository.getAll(
                page = currentPage,
                pageSize = pageSize,
                direction = SortDirection.DESC
            )

            if (newItems.isEmpty()) {
                _uiState.update { it.copy(isLoading = false, isLastPage = true) }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        historyItems = currentState.historyItems + newItems,
                        isLastPage = newItems.size < pageSize
                    )
                }
                currentPage++
            }
        }
    }

    fun getBeneficiaryName(id: Int): String {
        return beneficiaryRepository.getById(id)?.name ?: "Beneficiário Desconhecido"
    }
}
