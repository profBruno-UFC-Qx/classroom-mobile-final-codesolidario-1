package com.example.givchurch.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.auth.ForgotPasswordViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreen(
    onResetSuccess: () -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    vm: ForgotPasswordViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        vm.resetSuccess.collectLatest { success ->
            if (success) {
                onResetSuccess()
            }
        }
    }

    ForgotPasswordScreenContent(
        uiState = uiState,
        onEmailChange = vm::onEmailChange,
        onResetClick = vm::resetPassword,
        onBackToLoginClick = onBackToLoginClick,
        modifier = modifier
    )
}
