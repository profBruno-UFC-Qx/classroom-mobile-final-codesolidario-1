package com.example.givchurch.ui.screen.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.ui.component.donation.DonationItemCard
import com.example.givchurch.ui.theme.GivChurchTheme
import com.example.givchurch.viewmodel.donation.DonationUiState

@Composable
fun MainDonationContent(
    uiState: DonationUiState,
    onSearchQueryChanged: (String) -> Unit,
    onCategorySelected: (DonationCategory?) -> Unit,
    onAddDonationClick: () -> Unit,
    onEditDonationClick: (Donation) -> Unit,
    onDeleteDonationClick: (Donation) -> Unit,
    onLoadBeneficiaryName: (Int, (String) -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    var localSearchQuery by remember { mutableStateOf(uiState.searchQuery) }

    LaunchedEffect(uiState.searchQuery) {
        if (localSearchQuery != uiState.searchQuery) {
            localSearchQuery = uiState.searchQuery
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Text(
                    text = "Doações",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = localSearchQuery,
                    onValueChange = { newQuery ->
                        localSearchQuery = newQuery
                        onSearchQueryChanged(newQuery)
                    },
                    placeholder = { Text("Buscar doações...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtro",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(end = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = uiState.selectedCategory == null,
                            onClick = { onCategorySelected(null) },
                            label = { Text("Todos") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                selectedLabelColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }

                    items(DonationCategory.entries.toTypedArray()) { category ->
                        FilterChip(
                            selected = uiState.selectedCategory == category,
                            onClick = { onCategorySelected(category) },
                            label = { Text(category.value) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                selectedLabelColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.donationsList) { donation ->
                    var beneficiaryName by remember { mutableStateOf("Carregando...") }

                    LaunchedEffect(key1 = donation.beneficiaryId) {
                        onLoadBeneficiaryName(donation.beneficiaryId) { name ->
                            beneficiaryName = name
                        }
                    }

                    DonationItemCard(
                        donation = donation,
                        beneficiaryName = beneficiaryName,
                        onEditClick = { onEditDonationClick(donation) },
                        onDeleteClick = { onDeleteDonationClick(donation) }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onAddDonationClick,
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Adicionar Doação")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainDonationScreenPreview() {
    GivChurchTheme(darkTheme = false) {
        val domainMockItems = com.example.givchurch.data.mock.DonationMockData.donations.map { entity ->
            Donation(
                id = entity.id,
                imageUrl = entity.imageUrl,
                name = entity.name,
                category = DonationCategory.FOOD,
                description = entity.description,
                quantity = entity.quantity,
                beneficiaryId = entity.beneficiaryId,
                createBy = entity.createBy,
                status = DonationStatus.DELIVERED,
                createdAt = entity.createdAt,
                dueDate = entity.dueDate
            )
        }

        MainDonationContent(
            uiState = DonationUiState(
                searchQuery = "",
                selectedCategory = null,
                donationsList = domainMockItems
            ),
            onSearchQueryChanged = {},
            onCategorySelected = {},
            onAddDonationClick = {},
            onEditDonationClick = {},
            onDeleteDonationClick = {},
            onLoadBeneficiaryName = { _, onResult -> onResult("Atendido de Teste") }
        )
    }
}
