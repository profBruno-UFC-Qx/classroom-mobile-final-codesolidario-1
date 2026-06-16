package com.example.givchurch.ui.screen.beneficiary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.givchurch.ui.component.BeneficiaryItemCard
import com.example.givchurch.viewmodel.beneficiary.BeneficiaryUiState

@Composable
fun MainBeneficiaryContent(
    uiState: BeneficiaryUiState,
    onSearchQueryChanged: (String) -> Unit,
    onAddBeneficiaryClick: () -> Unit,
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
                    text = "Beneficiários",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = { Text("Buscar beneficiários...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Ícone de busca"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 88.dp
                )
            ) {
                items(uiState.beneficiariesList) { beneficiary ->
                    BeneficiaryItemCard(beneficiary = beneficiary)
                }
            }
        }

        FloatingActionButton(
            onClick = onAddBeneficiaryClick,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar Beneficiário"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainBeneficiaryScreenPreview() {
    MaterialTheme {
        val domainMockList = com.example.givchurch.data.mock.BeneficiaryMockData.beneficiaries.map { entity ->
            com.example.givchurch.domain.model.Beneficiary(
                id = entity.id,
                name = entity.name,
                phoneNumber = entity.phoneNumber,
                address = entity.address,
                observations = entity.observations,
                createBy = entity.createBy
            )
        }

        MainBeneficiaryContent(
            uiState = BeneficiaryUiState(
                searchQuery = "",
                beneficiariesList = domainMockList
            ),
            onSearchQueryChanged = {},
            onAddBeneficiaryClick = {}
        )
    }
}
