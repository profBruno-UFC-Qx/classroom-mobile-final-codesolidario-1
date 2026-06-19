package com.example.givchurch.domain.model

data class User(
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val imageUrl: String = ""
)
