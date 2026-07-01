package com.example.givchurch.viewmodel.auth

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
