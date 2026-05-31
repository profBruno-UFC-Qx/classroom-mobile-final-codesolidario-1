package com.example.givchurch.viewmodel.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainProfileViewModel : ViewModel() {

    private val _isLightTheme = MutableStateFlow(false)
    val isLightTheme: StateFlow<Boolean> = _isLightTheme.asStateFlow()

    private val _isNotificationsEnabled = MutableStateFlow(true)
    val isNotificationsEnabled: StateFlow<Boolean> = _isNotificationsEnabled.asStateFlow()

    fun toggleTheme(enabled: Boolean) {
        _isLightTheme.update { enabled }
    }

    fun toggleNotifications(enabled: Boolean) {
        _isNotificationsEnabled.update { enabled }
    }

    fun onHelpClick() {
        // TODO: Lógica para abrir ajuda
    }

    fun onPrivacyPolicyClick() {
        // TODO: Lógica para abrir política de privacidade
    }

    fun onLogoutClick() {
        // TODO: Lógica para deslogar o usuário
    }
}
