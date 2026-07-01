package com.example.givchurch.viewmodel.home

import com.example.givchurch.domain.model.DashboardMetrics
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.MonthlyDonation

sealed interface DashboardUiState {
    object Loading : DashboardUiState
    data class Success(
        val userName: String,
        val metrics: DashboardMetrics,
        val monthlyDonations: List<MonthlyDonation>,
        val recentDonations: List<Donation>
    ) : DashboardUiState
}
