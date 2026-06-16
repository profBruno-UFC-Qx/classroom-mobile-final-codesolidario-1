package com.example.givchurch.data.repository

import com.example.givchurch.data.local.dao.DashboardDao
import com.example.givchurch.domain.model.MonthlyDonation
import com.example.givchurch.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

class DashboardRepositoryImpl(
    private val dashboardDao: DashboardDao
) : DashboardRepository {

    override fun getTotalDonations(): Flow<Int> {
        return dashboardDao.getTotalDonations()
    }

    override fun getPendingDonations(): Flow<Int> {
        return dashboardDao.getPendingDonations()
    }

    override fun getDeliveredDonations(): Flow<Int> {
        return dashboardDao.getDeliveredDonations()
    }

    override fun getTotalBeneficiaries(): Flow<Int> {
        return dashboardDao.getTotalBeneficiaries()
    }

    override fun getMonthlyDonations(): Flow<List<MonthlyDonation>> {
        val now = LocalDateTime.now()
        val currentYearStr = now.year.toString()

        val pastFiveMonths = (4 downTo 0).map { now.minusMonths(it.toLong()) }

        return dashboardDao.getDonationsForYear(currentYearStr).map { donationList ->
            val groupedByMonth = donationList.groupBy { it.monthStr }

            pastFiveMonths.map { targetDateTime ->
                val monthKey = String.format(Locale.US, "%02d", targetDateTime.monthValue)

                val totalQuantity = groupedByMonth[monthKey]?.sumOf { it.quantity } ?: 0

                val displayName = targetDateTime.month
                    .getDisplayName(
                        TextStyle.SHORT,
                        Locale.Builder().setLanguage("pt").setRegion("BR").build()
                    )
                    .replaceFirstChar { it.uppercase() }
                    .take(3)

                MonthlyDonation(
                    monthName = displayName,
                    totalAmount = totalQuantity
                )
            }
        }
    }
}
