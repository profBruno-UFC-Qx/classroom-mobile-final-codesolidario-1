package com.example.givchurch.viewmodel.donation

import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory

data class DonationUiState(
    val searchQuery: String = "",
    val selectedCategory: DonationCategory? = null,
    val donationsList: List<Donation> = emptyList()
)