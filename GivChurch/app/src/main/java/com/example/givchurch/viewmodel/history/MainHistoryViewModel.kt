package com.example.givchurch.viewmodel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.data.model.Donation
import com.example.givchurch.data.repository.DonationRepository
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
    private val repository: DonationRepository = DonationRepository() // Injetando o Repositório
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private val pageSize = 5 // Ajustado para 5 para uma melhor experiência visual de rolagem

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        // Evita chamadas redundantes caso já esteja carregando ou tenha chegado ao fim do banco
        if (_uiState.value.isLoading || _uiState.value.isLastPage) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Busca os dados paginados diretamente através do Repositório
            val newItems = repository.getAll(
                page = currentPage,
                pageSize = pageSize,
                direction = SortDirection.DESC // Mais recentes primeiro
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
}
