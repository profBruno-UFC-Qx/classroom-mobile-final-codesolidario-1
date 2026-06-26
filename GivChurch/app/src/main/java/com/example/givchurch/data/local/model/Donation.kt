package com.example.givchurch.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.givchurch.data.local.model.enums.DonationCategory
import com.example.givchurch.data.local.model.enums.DonationStatus
import java.time.LocalDateTime

@Entity(
    tableName = "donations",
    foreignKeys = [
        ForeignKey(
            entity = Beneficiary::class,
            parentColumns = ["id"],
            childColumns = ["beneficiaryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["beneficiaryId"])]
)
data class Donation(
    @PrimaryKey(autoGenerate = true)
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
