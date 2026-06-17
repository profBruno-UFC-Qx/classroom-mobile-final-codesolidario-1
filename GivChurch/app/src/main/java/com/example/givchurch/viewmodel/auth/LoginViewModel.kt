package com.example.givchurch.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.User
import com.example.givchurch.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _loginSuccess = MutableSharedFlow<Boolean>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun clearFields() {
        _uiState.update { LoginUiState() }
    }

    fun login() {
        val currentState = _uiState.value

        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(message = "Por favor, preencha todos os campos obrigatórios.") }
            return
        }

        _uiState.update { it.copy(isLoading = true, message = "") }

        viewModelScope.launch {
            val userDomain = User(
                id = "",
                firstname = "",
                lastname = "",
                email = currentState.email,
                password = currentState.password
            )

            val result = repository.login(userDomain)

            result.fold(
                onSuccess = { successMessage ->
                    _uiState.update { it.copy(message = successMessage, isSuccess = true, isLoading = false) }
                    _loginSuccess.emit(true)
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            message = exception.localizedMessage ?: "Credenciais inválidas.",
                            isSuccess = false,
                            isLoading = false
                        )
                    }
                }
            )
        }
    }
}
