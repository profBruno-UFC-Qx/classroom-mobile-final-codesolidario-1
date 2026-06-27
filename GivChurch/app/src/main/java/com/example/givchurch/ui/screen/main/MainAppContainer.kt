package com.example.givchurch.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.ui.screen.beneficiary.MainBeneficiaryScreen
import com.example.givchurch.ui.screen.donation.MainDonationScreen
import com.example.givchurch.ui.screen.history.MainHistoryScreen
import com.example.givchurch.ui.screen.home.MainHomeScreen
import com.example.givchurch.ui.screen.profile.MainProfileScreen
import com.example.givchurch.ui.theme.GivChurchTheme

enum class NavigationItem(val title: String, val icon: ImageVector) {
    HOME(title = "Início", Icons.Default.Home),
    DONATIONS(title = "Doações", Icons.Default.CardGiftcard),
    BENEFICIARIES(title = "Atendidos", Icons.Default.People),
    HISTORY(title = "Histórico", Icons.Default.Refresh),
    PROFILE(title = "Perfil", Icons.Default.Person)
}

@Composable
fun MainAppContainer(
    selectedItem: NavigationItem,
    onTabSelected: (NavigationItem) -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    onAddBeneficiaryClick: () -> Unit,
    onAddDonationClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onEditProfileClick: () -> Unit,
    onEditBeneficiaryClick: (Beneficiary) -> Unit,
    onEditDonationClick: (Donation) -> Unit,
    onDonationClick: (Donation) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationItem.entries.forEach { item ->
                    val isSelected = selectedItem == item

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onTabSelected(item) },
                        label = { Text(text = item.title) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.tertiary,
                            selectedTextColor = MaterialTheme.colorScheme.tertiary,
                            indicatorColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            when (selectedItem) {
                NavigationItem.HOME -> {
                    MainHomeScreen(modifier = Modifier.fillMaxSize())
                }
                NavigationItem.DONATIONS -> {
                    MainDonationScreen(
                        onAddDonationClick = onAddDonationClick,
                        onDonationClick = onDonationClick
                    )
                }
                NavigationItem.BENEFICIARIES -> {
                    MainBeneficiaryScreen(
                        onAddBeneficiaryClick = onAddBeneficiaryClick,
                        onEditBeneficiaryClick = onEditBeneficiaryClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                NavigationItem.HISTORY -> {
                    MainHistoryScreen(modifier = Modifier.fillMaxSize())
                }
                NavigationItem.PROFILE -> {
                    MainProfileScreen(
                        isDarkTheme = isDarkTheme,
                        onThemeToggle = onThemeToggle,
                        onLogoutSuccess = onLogoutSuccess,
                        onEditProfileClick = onEditProfileClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Container Principal - Oficial")
@Composable
fun MainBeneficiaryScreenPreview() {
    GivChurchTheme(darkTheme = false) {
        MainAppContainer(
            selectedItem = NavigationItem.HOME,
            onTabSelected = {},
            isDarkTheme = false,
            onThemeToggle = {},
            onAddBeneficiaryClick = {},
            onAddDonationClick = {},
            onLogoutSuccess = {},
            onEditProfileClick = {},
            onEditBeneficiaryClick = {},
            onEditDonationClick = {},
            onDonationClick = {}
        )
    }
}
