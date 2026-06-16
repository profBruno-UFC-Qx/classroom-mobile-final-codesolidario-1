package com.example.givchurch.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.DashboardMetrics
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.MonthlyDonation
import com.example.givchurch.domain.repository.DashboardRepository
import com.example.givchurch.domain.repository.DonationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainHomeViewModel(
    private val dashboardRepository: DashboardRepository,
    private val donationRepository: DonationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardMetrics()
    }

    private fun loadDashboardMetrics() {
        viewModelScope.launch {
            combine(
                dashboardRepository.getTotalDonations(),
                dashboardRepository.getPendingDonations(),
                dashboardRepository.getDeliveredDonations(),
                dashboardRepository.getTotalBeneficiaries(),
                dashboardRepository.getMonthlyDonations()
            ) { total, pending, delivered, beneficiaries, monthlyDonations ->
                Triple(
                    DashboardMetrics(total, pending, delivered, beneficiaries),
                    monthlyDonations,
                    null
                )
            }.combine(donationRepository.getAll(limit = 5)) { dashboardData, recentDonations ->
                DashboardUiState.Success(
                    metrics = dashboardData.first,
                    monthlyDonations = dashboardData.second,
                    recentDonations = recentDonations
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}

sealed interface DashboardUiState {
    object Loading : DashboardUiState
    data class Success(
        val metrics: DashboardMetrics,
        val monthlyDonations: List<MonthlyDonation>,
        val recentDonations: List<Donation>
    ) : DashboardUiState
}
