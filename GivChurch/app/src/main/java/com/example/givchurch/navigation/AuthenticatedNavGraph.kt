package com.example.givchurch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.givchurch.ui.screen.beneficiary.AddBeneficiaryScreen
import com.example.givchurch.ui.screen.main.MainAppContainer

@Composable
fun AuthenticatedNavGraph(
    modifier: Modifier = Modifier
) {
    val internalBackStack = rememberSaveable { mutableStateListOf<Screen>(Screen.MainBeneficiaryScreen) }

    NavDisplay(
        backStack = internalBackStack,
        onBack = { internalBackStack.removeLastOrNull() },
        entryProvider = entryProvider {

            entry<Screen.MainBeneficiaryScreen> {
                MainAppContainer(
                    onAddBeneficiaryClick = {
                        internalBackStack.add(Screen.AddRegisterScreen)
                    }
                )
            }

            entry<Screen.AddRegisterScreen> {
                AddBeneficiaryScreen(
                    onNavigateBack = {
                        internalBackStack.removeLastOrNull()
                    }
                )
            }
        },
        modifier = modifier
    )
}
