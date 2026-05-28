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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.givchurch.ui.screen.organization.MainOrganizationScreen

enum class NavigationItem(val title: String, val icon: ImageVector) {
    HOME(title = "Início", Icons.Default.Home),
    DONATIONS(title = "Doações", Icons.Default.CardGiftcard),
    BENEFICIARIES(title = "Atendidos", Icons.Default.People),
    HISTORY(title = "Histórico", Icons.Default.Refresh),
    PROFILE(title = "Perfil", Icons.Default.Person)
}

@Composable
fun MainAppContainer(
    onAddOrganizationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf(NavigationItem.BENEFICIARIES) }

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
                        onClick = { selectedItem = item },
                        label = { Text(text = item.title) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
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
                .padding(paddingValues)
        ) {
            when (selectedItem) {
                NavigationItem.HOME -> PlaceholderScreen(title = "Tela Inicial")
                NavigationItem.DONATIONS -> PlaceholderScreen(title = "Tela de Doações")
                NavigationItem.BENEFICIARIES -> {
                    MainOrganizationScreen(onAddOrganizationClick = onAddOrganizationClick)
                }
                NavigationItem.HISTORY -> PlaceholderScreen(title = "Histórico de Atividades")
                NavigationItem.PROFILE -> PlaceholderScreen(title = "Perfil do Usuário")
            }
        }
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}
