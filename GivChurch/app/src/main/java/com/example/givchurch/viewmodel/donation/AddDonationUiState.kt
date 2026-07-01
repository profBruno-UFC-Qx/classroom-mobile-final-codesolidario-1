package com.example.givchurch.viewmodel.donation

import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import java.time.LocalDateTime

data class AddDonationUiState(
    val imageUrl: String? = null,
    val name: String = "",
    val description: String = "",
    val quantityString: String = "",
    val selectedCategory: DonationCategory? = null,
    val isCategoryExpanded: Boolean = false,
    val beneficiaries: List<Beneficiary> = emptyList(),
    val selectedBeneficiary: Beneficiary? = null,
    val isBeneficiaryExpanded: Boolean = false,
    val selectedStatus: DonationStatus = DonationStatus.PENDING,
    val isStatusExpanded: Boolean = false,
    val selectedDateTime: LocalDateTime = LocalDateTime.now().plusDays(1),
    val isDatePickerExpanded: Boolean = false,
    val isSaveSuccess: Boolean = false
)
