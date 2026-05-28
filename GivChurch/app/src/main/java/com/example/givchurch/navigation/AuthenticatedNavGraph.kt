package com.example.givchurch.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.givchurch.ui.screen.main.MainAppContainer
import com.example.givchurch.ui.screen.organization.AddOrganizationScreen

@Composable
fun AuthenticatedNavGraph(
    modifier: Modifier = Modifier
) {
    val internalBackStack = rememberSaveable { mutableStateListOf<Screen>(Screen.MainOrganizationScreen) }

    NavDisplay(
        backStack = internalBackStack,
        onBack = { internalBackStack.removeLastOrNull() },
        entryProvider = entryProvider {

            entry<Screen.MainOrganizationScreen> {
                MainAppContainer(
                    onAddOrganizationClick = {
                        internalBackStack.add(Screen.AddRegisterScreen)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Screen.AddRegisterScreen> {
                AddOrganizationScreen(
                    onNavigateBack = {
                        internalBackStack.removeLastOrNull()
                    },
                    modifier = Modifier
                )
            }
        },
        modifier = modifier
    )
}
