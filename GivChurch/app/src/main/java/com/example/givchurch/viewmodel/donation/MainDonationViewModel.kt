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

    private val _operationState = MutableStateFlow(Triple(false, false, null as String?))

    val uiState: StateFlow<DonationUiState> = combine(
        _searchQuery,
        _selectedCategory,
        userRepository.getUserIdFlow(),
        _operationState
    ) { query, category, userId, operation ->
        listOf(query, category, userId, operation)
    }.flatMapLatest { args ->
        val query = args[0] as String
        val category = args[1] as DonationCategory?
        val userId = args[2] as String

        if (userId.isBlank()) {
            flowOf(emptyList())
        } else {
            donationRepository.searchAndFilter(name = query, category = category, createBy = userId)
        }
    }.map { list ->
        val operation = _operationState.value
        DonationUiState(
            searchQuery = _searchQuery.value,
            selectedCategory = _selectedCategory.value,
            donationsList = list,
            isLoading = operation.first,
            isSuccess = operation.second,
            errorMessage = operation.third
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
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

    fun updateDonation(donation: Donation) {
        _operationState.value = Triple(true, false, null)
        viewModelScope.launch {
            val isSuccess = donationRepository.update(donation)
            if (isSuccess) {
                _operationState.value = Triple(false, true, null)
            } else {
                _operationState.value = Triple(false, false, "Erro ao atualizar doação.")
            }
        }
    }

    fun deleteDonation(id: Int) {
        val currentUserId = userRepository.getCurrentUserId()
        if (currentUserId.isBlank()) {
            _operationState.value = Triple(false, false, "Usuário não autenticado.")
            return
        }

        _operationState.value = Triple(true, false, null)
        viewModelScope.launch {
            val isDeleted = donationRepository.delete(id = id, createBy = currentUserId)
            if (isDeleted) {
                _operationState.value = Triple(false, true, null)
            } else {
                _operationState.value = Triple(false, false, "Erro ao excluir doação.")
            }
        }
    }

    fun clearOperationStatus() {
        _operationState.value = Triple(false, false, null)
    }
}
