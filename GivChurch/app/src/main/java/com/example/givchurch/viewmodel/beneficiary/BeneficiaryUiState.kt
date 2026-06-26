package com.example.givchurch.viewmodel.beneficiary

import com.example.givchurch.domain.model.Beneficiary

data class BeneficiaryUiState(
    val searchQuery: String = "",
    val beneficiariesList: List<Beneficiary> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
