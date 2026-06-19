package com.example.givchurch.viewmodel.auth

import android.content.Context
import android.net.Uri
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
import java.io.File
import java.io.FileOutputStream

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _registerSuccess = MutableSharedFlow<Boolean>()
    val registerSuccess = _registerSuccess.asSharedFlow()

    fun onImageSelected(context: Context, uri: Uri?) {
        if (uri == null) return
        viewModelScope.launch {
            try {
                val fileName = "profile_${System.currentTimeMillis()}.jpg"
                val destinationFile = File(context.filesDir, fileName)
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    FileOutputStream(destinationFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                _uiState.update { it.copy(imageUrl = destinationFile.absolutePath) }
            } catch (e: Exception) {
                _uiState.update { it.copy(message = "Erro ao processar imagem de perfil.") }
            }
        }
    }

    fun onFirstnameChange(newValue: String) {
        _uiState.update { it.copy(firstname = newValue) }
    }

    fun onLastnameChange(newValue: String) {
        _uiState.update { it.copy(lastname = newValue) }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun resetRegisterStatus() {
        _uiState.value = RegisterUiState()
    }

    fun register() {
        val currentState = _uiState.value

        if (currentState.firstname.isBlank() || currentState.lastname.isBlank() ||
            currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(message = "Por favor, preencha todos os campos obrigatórios.") }
            return
        }

        _uiState.update { it.copy(isLoading = true, message = "") }

        viewModelScope.launch {
            val userDomain = User(
                id = "",
                firstname = currentState.firstname,
                lastname = currentState.lastname,
                email = currentState.email,
                password = currentState.password,
                imageUrl = _uiState.value.imageUrl
            )

            val result = repository.register(userDomain)

            result.fold(
                onSuccess = { successMessage ->
                    _uiState.update { it.copy(message = successMessage, isSuccess = true, isLoading = false) }
                    _registerSuccess.emit(true)
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            message = exception.localizedMessage ?: "Erro ao fazer o cadastro.",
                            isSuccess = false,
                            isLoading = false
                        )
                    }
                }
            )
        }
    }
}
