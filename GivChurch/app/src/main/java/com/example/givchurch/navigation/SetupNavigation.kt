package com.example.givchurch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.givchurch.ui.screen.auth.LoginScreen
import com.example.givchurch.ui.screen.auth.RegisterScreen
import com.example.givchurch.ui.screen.organization.AddOrganizationScreen
import com.example.givchurch.ui.screen.organization.MainOrganizationScreen

@Composable
fun SetupNavigation() {

    val backStack = rememberSaveable{ mutableStateListOf<Screen>(Screen.LoginScreen) }

    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = entryProvider {
            entry<Screen.LoginScreen>{
                LoginScreen(backStack)
            }

            entry<Screen.RegisterScreen>{
                RegisterScreen(backStack)
            }

            entry<Screen.MainOrganizationScreen>{
                MainOrganizationScreen(
                    onAddOrganizationClick = {
                        backStack.add(Screen.AddRegisterScreen)
                    }
                )
            }

            entry<Screen.AddRegisterScreen> {
                AddOrganizationScreen(
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}