package com.example.givchurch.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.auth.RegisterViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    vm: RegisterViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        vm.registerSuccess.collectLatest { success ->
            if (success) {
                onNavigateBack()
            }
        }
    }

    RegisterScreenContent(
        uiState = uiState,
        onFirstnameChange = vm::onFirstnameChange,
        onLastnameChange = vm::onLastnameChange,
        onEmailChange = vm::onEmailChange,
        onPasswordChange = vm::onPasswordChange,
        onRegisterClick = vm::register,
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}
