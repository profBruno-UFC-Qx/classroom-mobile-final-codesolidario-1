package com.example.givchurch.viewmodel.history

import com.example.givchurch.domain.model.Donation

data class HistoryUiState(
    val isLoading: Boolean = false,
    val historyItems: List<Donation> = emptyList()
)