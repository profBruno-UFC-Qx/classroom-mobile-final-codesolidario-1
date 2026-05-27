package com.example.givchurch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.givchurch.ui.screen.auth.LoginScreen
import com.example.givchurch.ui.screen.auth.RegisterScreen

@Composable
fun SetupNavigation() {

    val backStack = rememberSaveable{ mutableStateListOf<Screen>(Screen.Login) }

    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = entryProvider {
            entry<Screen.Login>{
                LoginScreen()
            }

            entry<Screen.Register>{
                RegisterScreen()
            }
        }
    )
}