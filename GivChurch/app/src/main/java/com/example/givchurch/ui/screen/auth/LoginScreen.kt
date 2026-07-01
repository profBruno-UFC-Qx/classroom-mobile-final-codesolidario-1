package com.example.givchurch.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.auth.LoginViewModel
import com.example.givchurch.viewmodel.bible.BibleViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = koinViewModel(),
    bibleViewModel: BibleViewModel = koinViewModel()
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val bibleUiState by bibleViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        loginViewModel.loginSuccess.collectLatest { success ->
            if (success) {
                onLoginSuccess()
            }
        }
    }

    LoginScreenContent(
        uiState = uiState,
        bibleUiState = bibleUiState,
        onEmailChange = loginViewModel::onEmailChange,
        onPasswordChange = loginViewModel::onPasswordChange,
        onLoginClick = loginViewModel::login,
        onCreateAccountClick = onCreateAccountClick,
        onForgotPasswordClick = onForgotPasswordClick,
        modifier = modifier
    )
}
