package com.example.givchurch.ui.screen.donation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.givchurch.data.local.model.Beneficiary
import com.example.givchurch.data.local.model.enums.DonationCategory
import com.example.givchurch.data.local.model.enums.DonationStatus
import com.example.givchurch.viewmodel.donation.AddDonationUiState
import com.example.givchurch.viewmodel.donation.AddDonationViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AddDonationScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddDonationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSaveSuccess) {
        if (uiState.isSaveSuccess) {
            onNavigateBack()
        }
    }

    AddDonationContent(
        uiState = uiState,
        onImageSelect = { viewModel.onImageSelected(it?.toString()) },
        onNameChange = viewModel::onNameChanged,
        onDescriptionChange = viewModel::onDescriptionChanged,
        onQuantityChange = viewModel::onQuantityChanged,
        onCategorySelect = viewModel::onCategorySelected,
        onCategoryExpandedChange = viewModel::onCategoryExpandedChanged,
        onBeneficiarySelect = viewModel::onBeneficiarySelected,
        onBeneficiaryExpandedChange = viewModel::onBeneficiaryExpandedChanged,
        onStatusSelect = viewModel::onStatusSelected,
        onStatusExpandedChange = viewModel::onStatusExpandedChanged,
        onDateSelect = viewModel::onDateSelected,
        onDatePickerExpandedChange = viewModel::onDatePickerExpandedChanged,
        onSaveClick = { viewModel.saveDonation() },
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDonationContent(
    uiState: AddDonationUiState,
    onImageSelect: (Uri?) -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onCategorySelect: (DonationCategory) -> Unit,
    onCategoryExpandedChange: (Boolean) -> Unit,
    onBeneficiarySelect: (Beneficiary) -> Unit,
    onBeneficiaryExpandedChange: (Boolean) -> Unit,
    onStatusSelect: (DonationStatus) -> Unit,
    onStatusExpandedChange: (Boolean) -> Unit,
    onDateSelect: (java.time.LocalDate) -> Unit,
    onDatePickerExpandedChange: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = onImageSelect
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Adicionar Nova Doação",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Foto do item",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { galleryLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Upload,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (uiState.imageUrl != null) "Foto selecionada!" else "Toque para adicionar foto",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Nome do item",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = onNameChange,
                    placeholder = { Text("Ex: Cesta Básica") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Categoria",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = uiState.isCategoryExpanded,
                    onExpandedChange = onCategoryExpandedChange
                ) {
                    OutlinedTextField(
                        value = uiState.selectedCategory?.value ?: "Selecione uma categoria",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isCategoryExpanded) },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = uiState.isCategoryExpanded,
                        onDismissRequest = { onCategoryExpandedChange(false) }
                    ) {
                        DonationCategory.entries.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.value) },
                                onClick = { onCategorySelect(category) }
                            )
                        }
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Descrição",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = onDescriptionChange,
                    placeholder = { Text("Descreva o item...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Quantidade",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = uiState.quantityString,
                    onValueChange = onQuantityChange,
                    placeholder = { Text("Ex: 5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // 1. Campo Beneficiário
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Beneficiário Destinatário",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = uiState.isBeneficiaryExpanded,
                    onExpandedChange = onBeneficiaryExpandedChange
                ) {
                    OutlinedTextField(
                        value = uiState.selectedBeneficiary?.name ?: "Selecione o beneficiário",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isBeneficiaryExpanded) },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (uiState.beneficiaries.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = uiState.isBeneficiaryExpanded,
                            onDismissRequest = { onBeneficiaryExpandedChange(false) }
                        ) {
                            uiState.beneficiaries.forEach { beneficiary ->
                                DropdownMenuItem(
                                    text = { Text(beneficiary.name) },
                                    onClick = { onBeneficiarySelect(beneficiary) }
                                )
                            }
                        }
                    }
                }
            }

            // 2. Campo Status da Doação
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Status da Doação",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = uiState.isStatusExpanded,
                    onExpandedChange = onStatusExpandedChange
                ) {
                    OutlinedTextField(
                        value = uiState.selectedStatus.value,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isStatusExpanded) },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = uiState.isStatusExpanded,
                        onDismissRequest = { onStatusExpandedChange(false) }
                    ) {
                        DonationStatus.entries.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.value) },
                                onClick = { onStatusSelect(status) }
                            )
                        }
                    }
                }
            }

            // 3. Campo Prazo Limite
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Prazo limite para entrega",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = uiState.selectedDate.format(dateFormatter),
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        TextButton(onClick = { onDatePickerExpandedChange(true) }) {
                            Text("Alterar")
                        }
                    }
                )
            }

            // 4. Caixa de Diálogo do Calendário (DatePicker)
            if (uiState.isDatePickerExpanded) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = uiState.selectedDate.atStartOfDay()
                        .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                )

                DatePickerDialog(
                    onDismissRequest = { onDatePickerExpandedChange(false) },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                onDateSelect(
                                    Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                )
                            }
                        }) { Text("Confirmar") }
                    },
                    dismissButton = {
                        TextButton(onClick = { onDatePickerExpandedChange(false) }) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Botão de Envio
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = uiState.name.isNotBlank() && uiState.selectedCategory != null && uiState.selectedBeneficiary != null
            ) {
                Text(
                    text = "Salvar Doação",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddBeneficiaryScreenPreview() {
    MaterialTheme {
        AddDonationScreen(onNavigateBack = {})
    }
}