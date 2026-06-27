package com.example.givchurch.ui.screen.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import com.example.givchurch.viewmodel.profile.MainProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainProfileScreen(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    onLogoutSuccess: () -> Unit,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.logoutSuccess.collectLatest { success ->
            if (success) {
                onLogoutSuccess()
            }
        }
    }

    val updatedUiState = uiState.copy(isLightTheme = !isDarkTheme)

    MainProfileContent(
        uiState = updatedUiState,
        onThemeToggle = { isChecked ->
            viewModel.toggleTheme(isChecked)
            onThemeToggle(isChecked)
        },
        onNotificationsToggle = viewModel::toggleNotifications,
        onHelpClick = viewModel::onHelpClick,
        onPrivacyPolicyClick = viewModel::onPrivacyPolicyClick,
        onLogoutClick = viewModel::onLogoutClick,
        onEditProfileClick = onEditProfileClick,
        modifier = modifier
    )
}
