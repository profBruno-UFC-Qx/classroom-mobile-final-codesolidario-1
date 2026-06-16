package com.example.givchurch.viewmodel.beneficiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.repository.BeneficiaryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class MainBeneficiaryViewModel(
    private val repository: BeneficiaryRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<BeneficiaryUiState> = _searchQuery
        .flatMapLatest { query ->
            val flowResult = if (query.isBlank()) {
                repository.getAll()
            } else {
                repository.getByName(query)
            }
            flowResult.map { list ->
                BeneficiaryUiState(
                    searchQuery = query,
                    beneficiariesList = list
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BeneficiaryUiState()
        )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
