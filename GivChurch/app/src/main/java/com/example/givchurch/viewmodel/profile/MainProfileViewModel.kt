package com.example.givchurch.viewmodel.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileUiState(
    val isLightTheme: Boolean = false,
    val isNotificationsEnabled: Boolean = true
)

class MainProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleTheme(enabled: Boolean) {
        _uiState.update { it.copy(isLightTheme = enabled) }
    }

    fun toggleNotifications(enabled: Boolean) {
        _uiState.update { it.copy(isNotificationsEnabled = enabled) }
    }

    fun onHelpClick() {
        // TODO
    }

    fun onPrivacyPolicyClick() {
        // TODO
    }

    fun onLogoutClick() {
        // TODO
    }
}
