package com.example.givchurch.navigation

sealed class Screen {

    data object LoginScreen: Screen()
    data object ForgotPasswordScreen : Screen()
    data object RegisterScreen: Screen()

    data object MainBeneficiaryScreen: Screen()
    data object AddRegisterScreen: Screen()

    data object MainDonationScreen : Screen()
    data object AddDonationScreen : Screen()
}