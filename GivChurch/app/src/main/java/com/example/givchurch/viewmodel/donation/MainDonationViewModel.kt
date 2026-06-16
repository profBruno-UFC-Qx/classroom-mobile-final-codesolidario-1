package com.example.givchurch.viewmodel.donation

import androidx.lifecycle.ViewModel
import com.example.givchurch.data.local.model.Donation
import com.example.givchurch.data.local.model.enums.DonationCategory
import com.example.givchurch.data.repository.DonationRepository
import com.example.givchurch.data.repository.BeneficiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DonationUiState(
    val searchQuery: String = "",
    val selectedCategory: DonationCategory? = null,
    val donationsList: List<Donation> = emptyList()
)

class MainDonationViewModel : ViewModel() {
    private val donationRepository = DonationRepository()
    private val beneficiaryRepository = BeneficiaryRepository()

    private val _uiState = MutableStateFlow(DonationUiState())
    val uiState: StateFlow<DonationUiState> = _uiState.asStateFlow()

    init {
        updateList()
    }

    fun onSearchQueryChanged(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }
        updateList()
    }

    fun onCategorySelected(category: DonationCategory?) {
        _uiState.update { it.copy(selectedCategory = category) }
        updateList()
    }

    private fun updateList() {
        val currentState = _uiState.value
        val filteredDonations = donationRepository.searchAndFilter(
            name = currentState.searchQuery,
            category = currentState.selectedCategory
        )
        _uiState.update { it.copy(donationsList = filteredDonations) }
    }

    fun getBeneficiaryName(id: Int): String {
        return beneficiaryRepository.getById(id)?.name ?: "Desconhecido"
    }
}
