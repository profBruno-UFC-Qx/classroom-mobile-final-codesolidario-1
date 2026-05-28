package com.example.givchurch.data.model

import com.example.givchurch.data.model.enums.DonationCategory
import com.example.givchurch.data.model.enums.DonationStatus

data class Donation(
    val id: Int = 0,
    val imageUrl: String? = null,
    val name: String,
    val category: DonationCategory,
    val description: String,
    val quantity: Int,
    val organizationId: Int,
    val createBy: Int,
    val status: DonationStatus = DonationStatus.PENDING
)
