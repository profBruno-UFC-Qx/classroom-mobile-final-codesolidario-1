package com.example.givchurch.ui.screen.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.profile.MainProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: MainProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MainProfileContent(
        uiState = uiState,
        onThemeToggle = viewModel::toggleTheme,
        onNotificationsToggle = viewModel::toggleNotifications,
        onHelpClick = viewModel::onHelpClick,
        onPrivacyPolicyClick = viewModel::onPrivacyPolicyClick,
        onLogoutClick = viewModel::onLogoutClick,
        modifier = modifier
    )
}
