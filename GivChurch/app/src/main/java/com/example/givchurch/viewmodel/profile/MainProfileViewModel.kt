package com.example.givchurch.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.repository.AuthRepository
import com.example.givchurch.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _settingsState = MutableStateFlow(Pair(false, true)) // Pair(isLightTheme, isNotificationsEnabled)

    private val _logoutSuccess = MutableSharedFlow<Boolean>()
    val logoutSuccess = _logoutSuccess.asSharedFlow()

    val uiState: StateFlow<ProfileUiState> = userRepository.getUserIdFlow()
        .flatMapLatest { userId ->
            if (userId.isBlank()) {
                flowOf(ProfileUiState())
            } else {
                combine(
                    _settingsState,
                    userRepository.getUserProfileFlow(userId)
                ) { settings, user ->
                    ProfileUiState(
                        isLightTheme = settings.first,
                        isNotificationsEnabled = settings.second,
                        userName = user?.let { "${it.firstname} ${it.lastname}".trim() } ?: "",
                        userEmail = user?.email ?: ""
                    )
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProfileUiState()
        )

    fun toggleTheme(enabled: Boolean) {
        _settingsState.update { it.copy(first = enabled) }
    }

    fun toggleNotifications(enabled: Boolean) {
        _settingsState.update { it.copy(second = enabled) }
    }

    fun onHelpClick() {
        // TODO: Implementar ajuda
    }

    fun onPrivacyPolicyClick() {
        // TODO: Implementar política de privacidade
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            authRepository.logout()
            _logoutSuccess.emit(true)
        }
    }
}
