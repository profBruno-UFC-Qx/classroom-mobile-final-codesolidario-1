package com.example.givchurch.viewmodel.beneficiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainBeneficiaryViewModel(
    private val repository: BeneficiaryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _operationState = MutableStateFlow(Triple(false, false, null as String?))

    val uiState: StateFlow<BeneficiaryUiState> = combine(
        _searchQuery,
        userRepository.getUserIdFlow(),
        _operationState
    ) { query, userId, operation ->
        Triple(query, userId, operation)
    }.flatMapLatest { (query, userId, _) ->
        if (userId.isBlank()) {
            flowOf(emptyList())
        } else {
            if (query.isBlank()) {
                repository.getAll(createBy = userId)
            } else {
                repository.getByName(name = query, createBy = userId)
            }
        }
    }.map { list ->
        val operation = _operationState.value
        BeneficiaryUiState(
            searchQuery = _searchQuery.value,
            beneficiariesList = list,
            isLoading = operation.first,
            isSuccess = operation.second,
            errorMessage = operation.third
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = BeneficiaryUiState()
    )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun updateBeneficiary(beneficiary: Beneficiary) {
        _operationState.value = Triple(true, false, null)
        viewModelScope.launch {
            val isSuccess = repository.update(beneficiary)
            if (isSuccess) {
                _operationState.value = Triple(false, true, null)
            } else {
                _operationState.value = Triple(false, false, "Erro ao atualizar atendido.")
            }
        }
    }

    fun deleteBeneficiary(id: Int) {
        val currentUserId = userRepository.getCurrentUserId()
        if (currentUserId.isBlank()) {
            _operationState.value = Triple(false, false, "Usuário não autenticado.")
            return
        }

        _operationState.value = Triple(true, false, null)
        viewModelScope.launch {
            val isDeleted = repository.delete(id = id, createBy = currentUserId)
            if (isDeleted) {
                _operationState.value = Triple(false, true, null)
            } else {
                _operationState.value = Triple(false, false, "Erro ao excluir atendido.")
            }
        }
    }

    fun clearOperationStatus() {
        _operationState.value = Triple(false, false, null)
    }
}
