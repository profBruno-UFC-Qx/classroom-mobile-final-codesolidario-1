package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.MonthlyDonation
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun getTotalDonations(createBy: String): Flow<Int>
    fun getPendingDonations(createBy: String): Flow<Int>
    fun getDeliveredDonations(createBy: String): Flow<Int>
    fun getTotalBeneficiaries(createBy: String): Flow<Int>
    fun getMonthlyDonations(createBy: String): Flow<List<MonthlyDonation>>
}
