package com.example.givchurch.viewmodel.donation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DonationDetailViewModel(
    private val donationRepository: DonationRepository,
    private val beneficiaryRepository: BeneficiaryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DonationDetailUiState())
    val uiState: StateFlow<DonationDetailUiState> = _uiState.asStateFlow()

    private val _donationId = MutableStateFlow<Int?>(null)

    val activeDonationFlow: StateFlow<DonationDetailUiState> = _donationId
        .flatMapLatest { id ->
            if (id == null) {
                flowOf(DonationDetailUiState(errorMessage = "Doação inválida."))
            } else {
                val userId = userRepository.getCurrentUserId()
                donationRepository.getById(id, createBy = userId).map { donation ->
                    if (donation != null) {
                        val beneficiary = beneficiaryRepository.getById(donation.beneficiaryId, createBy = userId)
                        DonationDetailUiState(
                            donation = donation,
                            beneficiaryName = beneficiary?.name ?: "Desconhecido"
                        )
                    } else {
                        DonationDetailUiState(errorMessage = "Doação não encontrada.")
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DonationDetailUiState(isLoading = true)
        )

    fun loadDonationDetails(id: Int) {
        _donationId.value = id
    }

    fun updateDonationStatus(newStatus: DonationStatus) {
        val currentDonation = activeDonationFlow.value.donation ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val updated = currentDonation.copy(status = newStatus)
            val success = donationRepository.update(updated)
            if (!success) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Falha ao atualizar o status.") }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteDonation() {
        val id = _donationId.value ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val userId = userRepository.getCurrentUserId()
            val success = donationRepository.delete(id, createBy = userId)
            if (success) {
                _uiState.update { it.copy(isLoading = false, isDeleteSuccess = true) }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Erro ao excluir doação.") }
            }
        }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun resetViewModel() {
        _donationId.value = null
        _uiState.update { DonationDetailUiState() }
    }

    override fun onCleared() {
        resetViewModel()
    }
}
