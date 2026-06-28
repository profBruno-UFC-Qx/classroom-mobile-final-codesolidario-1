package com.example.givchurch.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.model.User
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

    private val _settingsState = MutableStateFlow(Pair(false, true))
    private val _updateState = MutableStateFlow<Triple<Boolean, String?, String?>>(Triple(false, null, null))

    private val _logoutSuccess = MutableSharedFlow<Boolean>()
    val logoutSuccess = _logoutSuccess.asSharedFlow()

    val uiState: StateFlow<ProfileUiState> = userRepository.getUserIdFlow()
        .flatMapLatest { userId ->
            if (userId.isBlank()) {
                flowOf(ProfileUiState())
            } else {
                combine<Pair<Boolean, Boolean>, User?, Triple<Boolean, String?, String?>, ProfileUiState>(
                    _settingsState,
                    userRepository.getUserProfileFlow(userId),
                    _updateState
                ) { settings, user, update ->
                    ProfileUiState(
                        isLightTheme = settings.first,
                        isNotificationsEnabled = settings.second,
                        userName = user?.let { "${it.firstname} ${it.lastname}".trim() } ?: "",
                        userEmail = user?.email ?: "",
                        imageUrl = user?.imageUrl ?: "",
                        isLoading = update.first,
                        updateMessage = update.second,
                        errorMessage = update.third
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

    fun updateUserProfile(user: User) {
        viewModelScope.launch {
            _updateState.value = Triple(true, null, null)

            userRepository.updateProfile(user)
                .onSuccess { message ->
                    _updateState.value = Triple(false, message, null)
                }
                .onFailure { exception ->
                    _updateState.value = Triple(false, null, exception.message ?: "Erro ao atualizar")
                }
        }
    }

    fun onHelpClick() {
    }

    fun onPrivacyPolicyClick() {
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            authRepository.logout()
            _logoutSuccess.emit(true)
        }
    }

    fun clearUpdateMessages() {
        _updateState.value = Triple(false, null, null)
    }

}
