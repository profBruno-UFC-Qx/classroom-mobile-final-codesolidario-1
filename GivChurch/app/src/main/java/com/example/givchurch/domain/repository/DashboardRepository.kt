package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.MonthlyDonation
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun getTotalDonations(): Flow<Int>
    fun getPendingDonations(): Flow<Int>
    fun getDeliveredDonations(): Flow<Int>
    fun getTotalBeneficiaries(): Flow<Int>
    fun getMonthlyDonations(): Flow<List<MonthlyDonation>>
}
