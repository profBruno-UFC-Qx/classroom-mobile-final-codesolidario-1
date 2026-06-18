package com.example.givchurch.domain.model

import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import java.time.LocalDateTime

data class Donation(
    val id: Int = 0,
    val imageUrl: String? = null,
    val name: String,
    val category: DonationCategory,
    val description: String,
    val quantity: Int,
    val beneficiaryId: Int,
    val createBy: String,
    val status: DonationStatus = DonationStatus.PENDING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val dueDate: LocalDateTime
)
