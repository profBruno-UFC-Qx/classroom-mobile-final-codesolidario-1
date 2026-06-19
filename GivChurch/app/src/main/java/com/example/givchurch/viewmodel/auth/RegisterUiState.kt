package com.example.givchurch.viewmodel.auth

data class RegisterUiState(
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val imageUrl: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
