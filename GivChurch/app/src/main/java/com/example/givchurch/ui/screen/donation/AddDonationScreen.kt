package com.example.givchurch.ui.screen.donation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.givchurch.data.model.Donation
import com.example.givchurch.data.model.enums.DonationCategory
import com.example.givchurch.data.repository.BeneficiaryRepository
import com.example.givchurch.data.repository.DonationRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDonationScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Criar o viewmodel, apenas testanto a navegação

    val donationRepository = remember { DonationRepository() }
    val beneficiaryRepository = remember { BeneficiaryRepository() }
    val beneficiaries = remember { beneficiaryRepository.getAll() }

    // Estados dos campos do formulário
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quantityString by remember { mutableStateOf("") }

    // Estados dos Seletores (Dropdowns)
    var selectedCategory by remember { mutableStateOf(DonationCategory.FOOD) }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    // Se houver beneficiários cadastrados, seleciona o primeiro por padrão
    var selectedBeneficiary by remember { mutableStateOf(beneficiaries.firstOrNull()) }
    var beneficiaryDropdownExpanded by remember { mutableStateOf(false) }

    // Estado para a Data de Entrega (Prazo)
    var selectedDate by remember { mutableStateOf(LocalDate.now().plusDays(1)) }
    var datePickerExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Doação") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo: Nome do Item
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome do item") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Seleção de Categoria (Dropdown)
            ExposedDropdownMenuBox(
                expanded = categoryDropdownExpanded,
                onExpandedChange = { categoryDropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedCategory.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true).fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = categoryDropdownExpanded,
                    onDismissRequest = { categoryDropdownExpanded = false }
                ) {
                    DonationCategory.entries.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.value) },
                            onClick = {
                                selectedCategory = category
                                categoryDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Campo: Quantidade
            OutlinedTextField(
                value = quantityString,
                onValueChange = { quantityString = it },
                label = { Text("Quantidade") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Seleção de Beneficiário (Dropdown)
            ExposedDropdownMenuBox(
                expanded = beneficiaryDropdownExpanded,
                onExpandedChange = { beneficiaryDropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedBeneficiary?.name ?: "Nenhum beneficiário disponível",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Destinado para (Beneficiário)") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = beneficiaryDropdownExpanded) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true).fillMaxWidth()
                )
                if (beneficiaries.isNotEmpty()) {
                    ExposedDropdownMenu(
                        expanded = beneficiaryDropdownExpanded,
                        onDismissRequest = { beneficiaryDropdownExpanded = false }
                    ) {
                        beneficiaries.forEach { beneficiary ->
                            DropdownMenuItem(
                                text = { Text(beneficiary.name) },
                                onClick = {
                                    selectedBeneficiary = beneficiary
                                    beneficiaryDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Seletor de Data de Entrega (Prazo)
            val dateFormatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }
            OutlinedTextField(
                value = selectedDate.format(dateFormatter),
                onValueChange = {},
                readOnly = true,
                label = { Text("Prazo limite para entrega") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { datePickerExpanded = true }) {
                        Text("Mudar")
                    }
                }
            )

            // Componente de Calendário (DatePickerDialog)
            if (datePickerExpanded) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = selectedDate.atStartOfDay()
                        .atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
                )
                DatePickerDialog(
                    onDismissRequest = { datePickerExpanded = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                selectedDate = java.time.Instant.ofEpochMilli(millis)
                                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                            }
                            datePickerExpanded = false
                        }) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { datePickerExpanded = false }) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // Campo: Descrição/Observações
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição ou observações adicionais") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botão para salvar
            Button(
                onClick = {
                    val quantity = quantityString.toIntOrNull() ?: 1
                    val beneficiaryId = selectedBeneficiary?.id ?: 0

                    // Monta o objeto Donation com as informações recolhidas
                    val newDonation = Donation(
                        name = name,
                        category = selectedCategory,
                        description = description,
                        quantity = quantity,
                        beneficiaryId = beneficiaryId,
                        createBy = 1, // ID do Usuário mockado logado (Rubens)
                        dueDate = LocalDateTime.of(selectedDate, LocalTime.MIDNIGHT)
                    )

                    val success = donationRepository.create(newDonation)
                    if (success) {
                        onNavigateBack() // Retorna se salvou com sucesso
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && selectedBeneficiary != null
            ) {
                Text("Salvar Doação")
            }
        }
    }
}