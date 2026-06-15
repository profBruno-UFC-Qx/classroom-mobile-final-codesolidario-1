package com.example.givchurch.domain.model

data class Beneficiary(
    val id: Int = 0,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val observations: String,
    val createBy: Int,
)
