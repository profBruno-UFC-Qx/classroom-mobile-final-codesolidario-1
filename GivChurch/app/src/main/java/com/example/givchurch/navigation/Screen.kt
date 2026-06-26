package com.example.givchurch.navigation

import com.example.givchurch.domain.model.Beneficiary

sealed class Screen {

    data object LoginScreen: Screen()
    data object ForgotPasswordScreen : Screen()
    data object RegisterScreen: Screen()

    data object MainBeneficiaryScreen: Screen()

    data class AddRegisterScreen(
        val beneficiary: Beneficiary? = null
    ) : Screen()

    data object MainDonationScreen : Screen()
    data class AddDonationScreen(
        val donation: com.example.givchurch.domain.model.Donation? = null
    ) : Screen()
}
