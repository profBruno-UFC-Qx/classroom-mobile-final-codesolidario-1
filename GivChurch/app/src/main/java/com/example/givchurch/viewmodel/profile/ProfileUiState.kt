package com.example.givchurch.viewmodel.profile

data class ProfileUiState(
    val isLightTheme: Boolean = false,
    val isNotificationsEnabled: Boolean = true,
    val userName: String = "",
    val userEmail: String = ""
)
