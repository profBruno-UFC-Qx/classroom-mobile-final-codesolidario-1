package com.example.givchurch.navigation

sealed class Screen {
    data object LoginScreen: Screen()
    data object RegisterScreen: Screen()
    data object MainBeneficiaryScreen: Screen()
    data object AddRegisterScreen: Screen()
}