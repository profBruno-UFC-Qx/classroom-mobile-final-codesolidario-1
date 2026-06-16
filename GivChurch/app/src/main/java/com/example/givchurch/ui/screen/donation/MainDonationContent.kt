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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.ui.component.DonationItemCard
import com.example.givchurch.viewmodel.donation.DonationUiState

@Composable
fun MainDonationContent(
    uiState: DonationUiState,
    onSearchQueryChanged: (String) -> Unit,
    onCategorySelected: (DonationCategory?) -> Unit,
    onAddDonationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Doações",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = { Text("Buscar doações...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtro",
                    modifier = Modifier.padding(end = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = uiState.selectedCategory == null,
                            onClick = { onCategorySelected(null) },
                            label = { Text("Todos") }
                        )
                    }

                    items(DonationCategory.entries.toTypedArray()) { category ->
                        FilterChip(
                            selected = uiState.selectedCategory == category,
                            onClick = { onCategorySelected(category) },
                            label = { Text(category.name) }
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.donationsList) { donation ->
                    DonationItemCard(
                        donation = donation,
                        beneficiaryName = donation.beneficiaryId.toString()
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onAddDonationClick,
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
    MaterialTheme {
        val domainMockItems = com.example.givchurch.data.mock.DonationMockData.donations.map { entity ->
            Donation(
                id = entity.id,
                imageUrl = entity.imageUrl,
                name = entity.name,
                category = DonationCategory.valueOf(entity.category.name),
                description = entity.description,
                quantity = entity.quantity,
                beneficiaryId = entity.beneficiaryId,
                createBy = entity.createBy,
                status = DonationStatus.valueOf(entity.status.name),
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
            onAddDonationClick = {}
        )
    }
}
