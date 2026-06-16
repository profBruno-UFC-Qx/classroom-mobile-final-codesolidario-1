package com.example.givchurch.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.ui.component.history.TimelineRow
import com.example.givchurch.viewmodel.history.HistoryUiState

@Composable
fun MainHistoryContent(
    uiState: HistoryUiState,
    loadBeneficiaryName: (Int, (String) -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Histórico",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Acompanhe todas as entregas",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(uiState.historyItems) { index, donation ->
                var beneficiaryName by remember(donation.id) { mutableStateOf("Carregando...") }

                LaunchedEffect(donation.beneficiaryId) {
                    loadBeneficiaryName(donation.beneficiaryId) { name ->
                        beneficiaryName = name
                    }
                }

                TimelineRow(
                    donation = donation,
                    beneficiaryName = beneficiaryName,
                    isLastItem = index == uiState.historyItems.lastIndex && !uiState.isLoading
                )
            }
            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainHistoryScreenPreview() {
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

        MainHistoryContent(
            uiState = HistoryUiState(
                isLoading = false,
                historyItems = domainMockItems
            ),
            loadBeneficiaryName = { _, onResult -> onResult("Instituição Parceira") }
        )
    }
}




