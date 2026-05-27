package com.example.givchurch.data.model

data class Organization(
    val id: Int = 0,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val observations: String,
    val createBy: Int,
)
