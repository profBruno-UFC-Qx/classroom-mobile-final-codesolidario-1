package com.example.givchurch.viewmodel.beneficiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class MainBeneficiaryViewModel(
    private val repository: BeneficiaryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<BeneficiaryUiState> = combine(
        _searchQuery,
        userRepository.getUserIdFlow()
    ) { query, userId ->
        Pair(query, userId)
    }.flatMapLatest { (query, userId) ->
        if (userId.isBlank()) {
            flowOf(emptyList())
        } else {
            if (query.isBlank()) {
                repository.getAll(createBy = userId)
            } else {
                repository.getByName(name = query, createBy = userId)
            }
        }
    }.map { list ->
        BeneficiaryUiState(
            searchQuery = _searchQuery.value,
            beneficiariesList = list
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BeneficiaryUiState()
    )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
