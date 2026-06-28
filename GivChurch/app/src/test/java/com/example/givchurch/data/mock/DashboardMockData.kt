package com.example.givchurch.data.mock

import com.example.givchurch.domain.model.MonthlyDonation

object DashboardMockData {

    val domainMonthlyDonations = listOf(
        MonthlyDonation(monthName = "Fev", totalAmount = 15),
        MonthlyDonation(monthName = "Mar", totalAmount = 25),
        MonthlyDonation(monthName = "Jun", totalAmount = 50)
    )
}
