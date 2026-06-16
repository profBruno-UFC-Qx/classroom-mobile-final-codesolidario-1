package com.example.givchurch.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beneficiaries")
data class Beneficiary(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val observations: String,
    val createBy: Int,
)
