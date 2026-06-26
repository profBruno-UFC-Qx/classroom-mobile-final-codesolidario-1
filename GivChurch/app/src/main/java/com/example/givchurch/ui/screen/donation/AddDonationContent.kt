package com.example.givchurch.ui.screen.donation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.ui.component.form.DonationDatePickerDialog
import com.example.givchurch.ui.component.form.DonationDatePickerField
import com.example.givchurch.ui.component.form.DonationDropdownMenu
import com.example.givchurch.ui.component.form.FormSectionLayout
import com.example.givchurch.ui.component.form.ImagePickerSelector
import com.example.givchurch.ui.theme.GivChurchTheme
import com.example.givchurch.viewmodel.donation.AddDonationUiState
import java.time.LocalDate

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
    onDateSelect: (LocalDate) -> Unit,
    onDatePickerExpandedChange: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    onNavigateBack: () -> Unit,
    isEditMode: Boolean = false,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = onImageSelect
    )

    if (uiState.isDatePickerExpanded) {
        DonationDatePickerDialog(
            initialDate = uiState.selectedDate,
            onDateConfirm = onDateSelect,
            onDismiss = { onDatePickerExpandedChange(false) }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isEditMode) "Editar Doação" else "Adicionar Nova Doação",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                FormSectionLayout(title = "Foto do item") {
                    ImagePickerSelector(
                        imageUrl = uiState.imageUrl,
                        onPickImage = { galleryLauncher.launch("image/*") }
                    )
                }

                FormSectionLayout(title = "Nome do item") {
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = onNameChange,
                        placeholder = { Text("Ex: Cesta Básica") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                FormSectionLayout(title = "Categoria") {
                    DonationDropdownMenu(
                        label = "Categoria",
                        options = DonationCategory.entries,
                        selectedOption = uiState.selectedCategory,
                        optionToString = { it.value },
                        isExpanded = uiState.isCategoryExpanded,
                        onExpandedChange = onCategoryExpandedChange,
                        onOptionSelect = onCategorySelect,
                        placeholderText = "Selecione uma categoria"
                    )
                }

                FormSectionLayout(title = "Descrição") {
                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = onDescriptionChange,
                        placeholder = { Text("Descreva o item...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                FormSectionLayout(title = "Quantidade") {
                    OutlinedTextField(
                        value = uiState.quantityString,
                        onValueChange = onQuantityChange,
                        placeholder = { Text("Ex: 5") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                FormSectionLayout(title = "Atendido Destinatário") {
                    DonationDropdownMenu(
                        label = "Atendido",
                        options = uiState.beneficiaries,
                        selectedOption = uiState.selectedBeneficiary,
                        optionToString = { it.name },
                        isExpanded = uiState.isBeneficiaryExpanded,
                        onExpandedChange = onBeneficiaryExpandedChange,
                        onOptionSelect = onBeneficiarySelect,
                        placeholderText = "Selecione o atendido"
                    )
                }

                FormSectionLayout(title = "Status da Doação") {
                    DonationDropdownMenu(
                        label = "Status",
                        options = DonationStatus.entries,
                        selectedOption = uiState.selectedStatus,
                        optionToString = { it.name },
                        isExpanded = uiState.isStatusExpanded,
                        onExpandedChange = onStatusExpandedChange,
                        onOptionSelect = onStatusSelect
                    )
                }

                FormSectionLayout(title = "Prazo limite para entrega") {
                    DonationDatePickerField(
                        selectedDate = uiState.selectedDate,
                        onCalendarClick = { onDatePickerExpandedChange(true) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(


                    onClick = onSaveClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    enabled = uiState.name.isNotBlank() && uiState.selectedCategory != null &&
                            uiState.selectedBeneficiary != null
                ) {
                    Text(
                        text = if (isEditMode) "Salvar Alterações" else "Salvar Doação",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true, name = "Adicionar Doação - Oficial")
@Composable
fun AddDonationScreenPreview() {
    GivChurchTheme(darkTheme = false) {
        val domainMockBeneficiaries =
            com.example.givchurch.data.mock.BeneficiaryMockData.beneficiaries.map { entity ->
                Beneficiary(
                    id = entity.id,
                    name = entity.name,
                    phoneNumber = entity.phoneNumber,
                    address = entity.address,
                    observations = entity.observations,
                    createBy = entity.createBy
                )
            }
        AddDonationContent(
            uiState = AddDonationUiState(
                beneficiaries = domainMockBeneficiaries
            ),
            onImageSelect = {},
            onNameChange = {},
            onDescriptionChange = {},
            onQuantityChange = {},
            onCategorySelect = {},
            onCategoryExpandedChange = {},
            onBeneficiarySelect = {},
            onBeneficiaryExpandedChange = {},
            onStatusSelect = {},
            onStatusExpandedChange = {},
            onDateSelect = {},
            onDatePickerExpandedChange = {},
            onSaveClick = {},
            onNavigateBack = {}
        )
    }
}

