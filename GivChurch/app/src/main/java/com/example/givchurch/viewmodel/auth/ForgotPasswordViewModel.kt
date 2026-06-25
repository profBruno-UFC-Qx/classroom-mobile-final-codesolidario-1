package com.example.givchurch.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    private val _resetSuccess = MutableSharedFlow<Boolean>()
    val resetSuccess = _resetSuccess.asSharedFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun clearFields() {
        _uiState.update { ForgotPasswordUiState() }
    }

    fun resetPassword() {
        val currentState = _uiState.value

        if (currentState.email.isBlank()) {
            _uiState.update { it.copy(message = "Por favor, preencha o campo de e-mail.") }
            return
        }

        _uiState.update { it.copy(isLoading = true, message = "") }

        viewModelScope.launch {
            val result = repository.resetPassword(currentState.email)

            result.fold(
                onSuccess = { successMessage ->
                    _uiState.update {
                        it.copy(
                            message = successMessage,
                            isSuccess = true,
                            isLoading = false
                        )
                    }
                    _resetSuccess.emit(true)
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            message = exception.localizedMessage ?: "Ocorreu um erro ao enviar o e-mail.",
                            isSuccess = false,
                            isLoading = false
                        )
                    }
                }
            )
        }
    }
}
