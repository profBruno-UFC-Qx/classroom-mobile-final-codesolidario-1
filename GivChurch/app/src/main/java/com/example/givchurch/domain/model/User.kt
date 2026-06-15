package com.example.givchurch.domain.model

data class User(
    val id: Int = 0,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
)
