package com.example.givchurch.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.DashboardMetrics
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.MonthlyDonation
import com.example.givchurch.domain.repository.DashboardRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import com.example.givchurch.domain.repository.enums.SortDirection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class MainHomeViewModel(
    private val dashboardRepository: DashboardRepository,
    private val donationRepository: DonationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = userRepository.getUserIdFlow()
        .flatMapLatest { userId ->
            if (userId.isBlank()) {
                flowOf(DashboardUiState.Loading)
            } else {
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
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardUiState.Loading
        )
}
