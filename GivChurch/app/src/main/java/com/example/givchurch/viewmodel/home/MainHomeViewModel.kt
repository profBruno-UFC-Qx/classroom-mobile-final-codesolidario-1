package com.example.givchurch.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.DashboardMetrics
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
import kotlinx.coroutines.flow.map
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
                val metricsFlow = combine(
                    dashboardRepository.getTotalDonations(createBy = userId),
                    dashboardRepository.getPendingDonations(createBy = userId),
                    dashboardRepository.getDeliveredDonations(createBy = userId),
                    dashboardRepository.getTotalBeneficiaries(createBy = userId)
                ) { total, pending, delivered, beneficiaries ->
                    DashboardMetrics(total, pending, delivered, beneficiaries)
                }

                val userProfileFlow = userRepository.getUserProfileFlow(userId)
                    .map { user -> user?.firstname ?: "Voluntário" }

                combine(
                    metricsFlow,
                    dashboardRepository.getMonthlyDonations(createBy = userId),
                    donationRepository.getAll(direction = SortDirection.DESC, limit = 5, createBy = userId),
                    userProfileFlow
                ) { metrics, monthlyDonations, recentDonations, name ->
                    DashboardUiState.Success(
                        userName = name,
                        metrics = metrics,
                        monthlyDonations = monthlyDonations,
                        recentDonations = recentDonations
                    )
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DashboardUiState.Loading
        )
}
