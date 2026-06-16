package com.example.givchurch.viewmodel.donation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DonationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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
    val selectedDate: LocalDate = LocalDate.now().plusDays(1),
    val isDatePickerExpanded: Boolean = false,
    val isSaveSuccess: Boolean = false
)

class AddDonationViewModel(
    private val donationRepository: DonationRepository,
    private val beneficiaryRepository: BeneficiaryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddDonationUiState())
    val uiState: StateFlow<AddDonationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            beneficiaryRepository.getAll().collect { list ->
                _uiState.update { it.copy(beneficiaries = list) }
            }
        }
    }

    fun onImageSelected(uri: String?) = _uiState.update { it.copy(imageUrl = uri) }
    fun onNameChanged(newName: String) = _uiState.update { it.copy(name = newName) }
    fun onDescriptionChanged(newDesc: String) = _uiState.update { it.copy(description = newDesc) }
    fun onQuantityChanged(newQty: String) = _uiState.update { it.copy(quantityString = newQty) }
    fun onCategorySelected(category: DonationCategory) = _uiState.update { it.copy(selectedCategory = category, isCategoryExpanded = false) }
    fun onCategoryExpandedChanged(expanded: Boolean) = _uiState.update { it.copy(isCategoryExpanded = expanded) }
    fun onBeneficiarySelected(beneficiary: Beneficiary) = _uiState.update { it.copy(selectedBeneficiary = beneficiary, isBeneficiaryExpanded = false) }
    fun onBeneficiaryExpandedChanged(expanded: Boolean) = _uiState.update { it.copy(isBeneficiaryExpanded = expanded) }
    fun onStatusSelected(status: DonationStatus) = _uiState.update { it.copy(selectedStatus = status, isStatusExpanded = false) }
    fun onStatusExpandedChanged(expanded: Boolean) = _uiState.update { it.copy(isStatusExpanded = expanded) }
    fun onDateSelected(date: LocalDate) = _uiState.update { it.copy(selectedDate = date, isDatePickerExpanded = false) }
    fun onDatePickerExpandedChanged(expanded: Boolean) = _uiState.update { it.copy(isDatePickerExpanded = expanded) }

    fun saveDonation() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val quantity = currentState.quantityString.toIntOrNull() ?: 1
            val beneficiaryId = currentState.selectedBeneficiary?.id ?: 0
            val category = currentState.selectedCategory ?: DonationCategory.FOOD

            val donation = Donation(
                id = 0,
                imageUrl = currentState.imageUrl,
                name = currentState.name,
                category = category,
                description = currentState.description,
                quantity = quantity,
                beneficiaryId = beneficiaryId,
                createBy = 1,
                status = currentState.selectedStatus,
                dueDate = LocalDateTime.of(currentState.selectedDate, LocalTime.MIDNIGHT)
            )

            val success = donationRepository.create(donation)
            if (success) {
                _uiState.update { it.copy(isSaveSuccess = true) }
            }
        }
    }
}
