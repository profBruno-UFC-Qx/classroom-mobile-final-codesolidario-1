package com.example.givchurch.viewmodel.profile

data class ProfileUiState(
    val isLightTheme: Boolean = false,
    val isNotificationsEnabled: Boolean = true,
    val userName: String = "",
    val userEmail: String = "",
    val imageUrl: String = "",
    val isLoading: Boolean = false,
    val updateMessage: String? = null,
    val errorMessage: String? = null
)
