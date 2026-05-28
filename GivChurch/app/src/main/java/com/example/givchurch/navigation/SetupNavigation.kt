package com.example.givchurch.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.givchurch.ui.screen.auth.LoginScreen
import com.example.givchurch.ui.screen.auth.RegisterScreen

@Composable
fun SetupNavigation(
    modifier: Modifier = Modifier
) {
    val backStack = rememberSaveable { mutableStateListOf<Screen>(Screen.LoginScreen) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.LoginScreen> {
                LoginScreen(
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(Screen.MainOrganizationScreen)
                    },
                    onCreateAccountClick = {
                        backStack.add(Screen.RegisterScreen)
                    },
                    modifier = Modifier
                )
            }

            entry<Screen.RegisterScreen> {
                RegisterScreen(
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    },
                    modifier = Modifier
                )
            }

            entry<Screen.MainOrganizationScreen> {
                AuthenticatedNavGraph(modifier = Modifier.fillMaxSize())
            }
        }
    )
}
