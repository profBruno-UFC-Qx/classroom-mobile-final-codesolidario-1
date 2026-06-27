package com.example.givchurch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.givchurch.ui.screen.auth.ForgotPasswordScreen
import com.example.givchurch.ui.screen.auth.LoginScreen
import com.example.givchurch.ui.screen.auth.RegisterScreen
import com.example.givchurch.viewmodel.auth.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetupNavigation(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val backStack = rememberSaveable { mutableStateListOf<Screen>(Screen.LoginScreen) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.LoginScreen> {
                LoginScreen(
                    onLoginSuccess = {
                        backStack.removeLastOrNull()
                        backStack.add(Screen.MainBeneficiaryScreen)
                    },
                    onCreateAccountClick = {
                        backStack.add(Screen.RegisterScreen)
                    },
                    onForgotPasswordClick = {
                        backStack.add(Screen.ForgotPasswordScreen)
                    },
                    modifier = Modifier
                )
            }

            entry<Screen.ForgotPasswordScreen> {
                ForgotPasswordScreen(
                    onResetSuccess = {
                        backStack.removeLastOrNull()
                    },
                    onBackToLoginClick = {
                        backStack.removeLastOrNull()
                    },
                    modifier = Modifier
                )
            }

            entry<Screen.RegisterScreen> {
                RegisterScreen(
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    },
                    isFromLogin = true,
                    modifier = Modifier
                )
            }

            entry<Screen.MainBeneficiaryScreen> {
                val loginVm: LoginViewModel = koinViewModel()

                AuthenticatedNavGraph(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = onThemeToggle,
                    onLogout = {
                        loginVm.clearFields()
                        while (backStack.isNotEmpty()) {
                            backStack.removeLastOrNull()
                        }
                        backStack.add(Screen.LoginScreen)
                    },
                    modifier = Modifier
                )
            }
        },
        modifier = modifier
    )
}
