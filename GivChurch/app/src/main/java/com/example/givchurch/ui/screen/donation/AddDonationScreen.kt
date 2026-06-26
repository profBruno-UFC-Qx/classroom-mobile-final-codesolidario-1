package com.example.givchurch.ui.screen.donation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.viewmodel.donation.AddDonationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddDonationScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    donation: Donation? = null,
    viewModel: AddDonationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = donation) {
        if (donation != null) {
            viewModel.loadDonationData(donation)
        }
    }

    LaunchedEffect(uiState.isSaveSuccess) {
        if (uiState.isSaveSuccess) {
            viewModel.resetSaveStatus()
            onNavigateBack()
        }
    }

    AddDonationContent(
        uiState = uiState,
        onImageSelect = { uri -> viewModel.onImageSelected(context, uri) },
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
        isEditMode = donation != null,
        modifier = modifier
    )
}
