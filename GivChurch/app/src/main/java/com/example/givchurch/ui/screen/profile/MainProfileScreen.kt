package com.example.givchurch.ui.screen.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.profile.MainProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainProfileScreen(
    onLogoutSuccess: () -> Unit,
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
