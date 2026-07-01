package com.example.givchurch.viewmodel.auth

data class ForgotPasswordUiState(
    val email: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
