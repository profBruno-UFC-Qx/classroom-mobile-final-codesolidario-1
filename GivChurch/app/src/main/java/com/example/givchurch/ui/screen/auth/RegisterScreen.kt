package com.example.givchurch.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.givchurch.viewmodel.auth.RegisterViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    isFromLogin: Boolean = false,
    vm: RegisterViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = isFromLogin) {
        if (isFromLogin) {
            vm.resetRegisterStatus()
        } else {
            vm.initEditMode()
        }
    }

    LaunchedEffect(key1 = true) {
        vm.registerSuccess.collectLatest { success ->
            if (success) {
                vm.resetRegisterStatus()
                onNavigateBack()
            }
        }
    }

    RegisterScreenContent(
        uiState = uiState,
        onImageSelect = { uri -> vm.onImageSelected(context, uri) },
        onFirstnameChange = vm::onFirstnameChange,
        onLastnameChange = vm::onLastnameChange,
        onEmailChange = vm::onEmailChange,
        onPasswordChange = vm::onPasswordChange,
        onRegisterClick = vm::register,
        onNavigateBack = {
            vm.resetRegisterStatus()
            onNavigateBack()
        },
        isEditMode = !isFromLogin,
        modifier = modifier
    )
}
