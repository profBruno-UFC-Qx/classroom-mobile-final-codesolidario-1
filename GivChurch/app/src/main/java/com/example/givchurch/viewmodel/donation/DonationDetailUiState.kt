package com.example.givchurch.viewmodel.donation

import com.example.givchurch.domain.model.Donation

data class DonationDetailUiState(
    val donation: Donation? = null,
    val beneficiaryName: String = "Carregando...",
    val isLoading: Boolean = false,
    val isDeleteSuccess: Boolean = false,
    val errorMessage: String? = null
)
