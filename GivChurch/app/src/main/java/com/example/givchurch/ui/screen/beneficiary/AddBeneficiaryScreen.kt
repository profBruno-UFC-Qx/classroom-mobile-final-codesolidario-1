package com.example.givchurch.ui.screen.beneficiary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.beneficiary.AddBeneficiaryViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddBeneficiaryScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddBeneficiaryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.saveSuccess.collectLatest { success ->
            if (success) {
                onNavigateBack()
            }
        }
    }

    AddBeneficiaryContent(
        uiState = uiState,
        onNameChanged = viewModel::onNameChanged,
        onPhoneChanged = viewModel::onPhoneChanged,
        onAddressChanged = viewModel::onAddressChanged,
        onObservationsChanged = viewModel::onObservationsChanged,
        onSaveClick = viewModel::saveBeneficiary,
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}
