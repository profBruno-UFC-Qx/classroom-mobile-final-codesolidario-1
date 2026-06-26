package com.example.givchurch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.givchurch.ui.screen.auth.RegisterScreen
import com.example.givchurch.ui.screen.beneficiary.AddBeneficiaryScreen
import com.example.givchurch.ui.screen.donation.AddDonationScreen
import com.example.givchurch.ui.screen.main.MainAppContainer
import com.example.givchurch.ui.screen.main.NavigationItem

@Composable
fun AuthenticatedNavGraph(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val internalBackStack = rememberSaveable { mutableStateListOf<Screen>(Screen.MainBeneficiaryScreen) }
    var currentTab by rememberSaveable { mutableStateOf(NavigationItem.HOME) }

    NavDisplay(
        backStack = internalBackStack,
        onBack = { internalBackStack.removeLastOrNull() },
        entryProvider = entryProvider {

            entry<Screen.MainBeneficiaryScreen> {
                MainAppContainer(
                    selectedItem = currentTab,
                    onTabSelected = { currentTab = it },
                    onAddBeneficiaryClick = {
                        internalBackStack.add(Screen.AddRegisterScreen)
                    },
                    onAddDonationClick = {
                        internalBackStack.add(Screen.AddDonationScreen)
                    },
                    onLogoutSuccess = onLogout,
                    onEditProfileClick = {
                        internalBackStack.add(Screen.RegisterScreen)
                    }
                )
            }

            entry<Screen.RegisterScreen> {
                RegisterScreen(
                    onNavigateBack = {
                        internalBackStack.removeLastOrNull()
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

            entry<Screen.AddDonationScreen> {
                AddDonationScreen(
                    onNavigateBack = {
                        internalBackStack.removeLastOrNull()
                    }
                )
            }
        },
        modifier = modifier
    )
}
