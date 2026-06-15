package com.example.givchurch.domain.model

data class DashboardMetrics(
    val totalDonations: Int,
    val pendingDonations: Int,
    val deliveredDonations: Int,
    val totalBeneficiaries: Int
)