package com.example.givchurch.viewmodel.beneficiary

data class AddBeneficiaryUiState(
    val name: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val observations: String = "",
    val errorMessage: String? = null
)
