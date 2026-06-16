package com.example.givchurch.data.repository

import com.example.givchurch.data.mock.BeneficiaryMockData
import com.example.givchurch.data.mock.DonationMockData
import com.example.givchurch.data.local.model.enums.DonationStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

data class DashboardMetrics(
    val totalDonations: Int,
    val pendingDonations: Int,
    val deliveredDonations: Int,
    val totalBeneficiaries: Int
)

data class MonthlyDonation(
    val monthName: String,
    val totalAmount: Int
)

class DashboardRepository {

    fun getTotalDonations(): Flow<Int> = flow {
        val total = DonationMockData.donations.sumOf { it.quantity }
        emit(total)
    }

    fun getPendingDonations(): Flow<Int> = flow {
        val pending = DonationMockData.donations
            .filter { it.status == DonationStatus.PENDING }
            .sumOf { it.quantity }
        emit(pending)
    }

    fun getDeliveredDonations(): Flow<Int> = flow {
        val delivered = DonationMockData.donations
            .filter { it.status == DonationStatus.DELIVERED }
            .sumOf { it.quantity }
        emit(delivered)
    }

    fun getTotalBeneficiaries(): Flow<Int> = flow {
        val totalBeneficiaries = BeneficiaryMockData.beneficiaries.size
        emit(totalBeneficiaries)
    }

    fun getMonthlyDonations(): Flow<List<MonthlyDonation>> = flow {
        val donations = DonationMockData.donations

        val currentYear = LocalDateTime.now().year
        val pastFiveMonths = (4 downTo 0).map {
            LocalDateTime.now().minusMonths(it.toLong()).month
        }

        val groupedData = donations
            .filter { it.createdAt.year == currentYear && it.createdAt.month in pastFiveMonths }
            .groupBy { it.createdAt.month }

        val monthlyList = pastFiveMonths.map { month ->
            val totalQuantity = groupedData[month]?.sumOf { it.quantity } ?: 0
            val displayName = month.getDisplayName(TextStyle.SHORT, Locale("pt", "BR"))
                .replaceFirstChar { it.uppercase() }
                .take(3)

            MonthlyDonation(
                monthName = displayName,
                totalAmount = totalQuantity
            )
        }

        emit(monthlyList)
    }
}
