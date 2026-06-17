package com.example.givchurch.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.auth.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
    vm: LoginViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        vm.loginSuccess.collectLatest { success ->
            if (success) {
                onLoginSuccess()
            }
        }
    }

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = vm::onEmailChange,
        onPasswordChange = vm::onPasswordChange,
        onLoginClick = vm::login,
        onCreateAccountClick = onCreateAccountClick,
        modifier = modifier
    )
}
