package com.example.givchurch.viewmodel.donation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DonationRepository
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainDonationViewModel(
    private val donationRepository: DonationRepository,
    private val beneficiaryRepository: BeneficiaryRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow<DonationCategory?>(null)

    val uiState: StateFlow<DonationUiState> = combine(
        _searchQuery,
        _selectedCategory,
        userRepository.getUserIdFlow()
    ) { query, category, userId ->
        Triple(query, category, userId)
    }.flatMapLatest { (query, category, userId) ->
        if (userId.isBlank()) {
            flowOf(emptyList())
        } else {
            donationRepository.searchAndFilter(name = query, category = category, createBy = userId)
        }
    }.map { list ->
        DonationUiState(
            searchQuery = _searchQuery.value,
            selectedCategory = _selectedCategory.value,
            donationsList = list
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DonationUiState()
    )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onCategorySelected(category: DonationCategory?) {
        _selectedCategory.value = category
    }

    fun loadBeneficiaryName(id: Int, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val userId = userRepository.getCurrentUserId()
            val beneficiary = beneficiaryRepository.getById(id, createBy = userId)
            onResult(beneficiary?.name ?: "Desconhecido")
        }
    }
}
