package com.example.givchurch.viewmodel.home

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.DashboardMetrics
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.MonthlyDonation
import com.example.givchurch.domain.repository.DashboardRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import com.example.givchurch.domain.repository.enums.SortDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainHomeViewModel(
    private val dashboardRepository: DashboardRepository,
    private val donationRepository: DonationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardMetrics()
    }

    private fun loadDashboardMetrics() {
        viewModelScope.launch {
            val userId = userRepository.getCurrentUserId()

            combine(
                dashboardRepository.getTotalDonations(createBy = userId),
                dashboardRepository.getPendingDonations(createBy = userId),
                dashboardRepository.getDeliveredDonations(createBy = userId),
                dashboardRepository.getTotalBeneficiaries(createBy = userId),
                dashboardRepository.getMonthlyDonations(createBy = userId)
            ) { total, pending, delivered, beneficiaries, monthlyDonations ->
                Triple(
                    DashboardMetrics(total, pending, delivered, beneficiaries),
                    monthlyDonations,
                    null
                )
            }.combine(donationRepository.getAll(direction = SortDirection.DESC, limit = 5, createBy = userId)) { dashboardData, recentDonations ->
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
