package com.example.givchurch.viewmodel.donation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

class AddDonationViewModel(
    private val donationRepository: DonationRepository,
    private val beneficiaryRepository: BeneficiaryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddDonationUiState())
    val uiState: StateFlow<AddDonationUiState> = _uiState.asStateFlow()

    private var currentDonationId: Int = 0
    private var isEditMode: Boolean = false

    init {
        viewModelScope.launch {
            val userId = userRepository.getCurrentUserId()
            beneficiaryRepository.getAll(createBy = userId).collect { list ->
                _uiState.update { it.copy(beneficiaries = list) }
            }
        }
    }

    fun loadDonationData(donation: Donation) {
        currentDonationId = donation.id
        isEditMode = true
        _uiState.update { currentState ->
            val matchingBeneficiary = currentState.beneficiaries.find { it.id == donation.beneficiaryId }
            currentState.copy(
                imageUrl = donation.imageUrl,
                name = donation.name,
                description = donation.description,
                quantityString = donation.quantity.toString(),
                selectedCategory = donation.category,
                selectedBeneficiary = matchingBeneficiary,
                selectedStatus = donation.status,
                selectedDateTime = donation.dueDate
            )
        }
    }

    fun onImageSelected(context: Context, uri: Uri?) {
        if (uri == null) return

        viewModelScope.launch {
            try {
                val fileName = "donation_${System.currentTimeMillis()}.jpg"
                val destinationFile = File(context.filesDir, fileName)

                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    FileOutputStream(destinationFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                val permanentImagePath = destinationFile.absolutePath
                _uiState.update { it.copy(imageUrl = permanentImagePath) }

            } catch (e: Exception) {
                Log.e("AddDonationViewModel", "Erro ao persistir imagem da galeria", e)
            }
        }
    }

    fun onNameChanged(newName: String) = _uiState.update { it.copy(name = newName) }
    fun onDescriptionChanged(newDesc: String) = _uiState.update { it.copy(description = newDesc) }
    fun onQuantityChanged(newQty: String) = _uiState.update { it.copy(quantityString = newQty) }
    fun onCategorySelected(category: DonationCategory) = _uiState.update { it.copy(selectedCategory = category, isCategoryExpanded = false) }
    fun onCategoryExpandedChanged(expanded: Boolean) = _uiState.update { it.copy(isCategoryExpanded = expanded) }
    fun onBeneficiarySelected(beneficiary: Beneficiary) = _uiState.update { it.copy(selectedBeneficiary = beneficiary, isBeneficiaryExpanded = false) }
    fun onBeneficiaryExpandedChanged(expanded: Boolean) = _uiState.update { it.copy(isBeneficiaryExpanded = expanded) }
    fun onStatusSelected(status: DonationStatus) = _uiState.update { it.copy(selectedStatus = status, isStatusExpanded = false) }
    fun onStatusExpandedChanged(expanded: Boolean) = _uiState.update { it.copy(isStatusExpanded = expanded) }
    fun onDateSelected(dateTime: LocalDateTime) = _uiState.update { it.copy(selectedDateTime = dateTime, isDatePickerExpanded = false) }
    fun onDatePickerExpandedChanged(expanded: Boolean) = _uiState.update { it.copy(isDatePickerExpanded = expanded) }

    fun resetSaveStatus() {
        isEditMode = false
        currentDonationId = 0
        _uiState.update { currentState ->
            AddDonationUiState(
                beneficiaries = currentState.beneficiaries
            )
        }
    }

    fun saveDonation() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val quantity = currentState.quantityString.toIntOrNull() ?: 1
            val beneficiaryId = currentState.selectedBeneficiary?.id ?: 0
            val category = currentState.selectedCategory ?: DonationCategory.FOOD

            val donation = Donation(
                id = currentDonationId,
                imageUrl = currentState.imageUrl,
                name = currentState.name,
                category = category,
                description = currentState.description,
                quantity = quantity,
                beneficiaryId = beneficiaryId,
                createBy = userRepository.getCurrentUserId(),
                status = currentState.selectedStatus,
                dueDate = currentState.selectedDateTime
            )

            val success = if (isEditMode) {
                donationRepository.update(donation)
            } else {
                donationRepository.create(donation)
            }

            if (success) {
                _uiState.update { it.copy(isSaveSuccess = true) }
            }
        }
    }
}
