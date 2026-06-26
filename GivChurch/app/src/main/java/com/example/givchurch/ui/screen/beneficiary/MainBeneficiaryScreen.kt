package com.example.givchurch.ui.screen.beneficiary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.viewmodel.beneficiary.MainBeneficiaryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainBeneficiaryScreen(
    onAddBeneficiaryClick: () -> Unit,
    onEditBeneficiaryClick: (Beneficiary) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainBeneficiaryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MainBeneficiaryContent(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onAddBeneficiaryClick = onAddBeneficiaryClick,
        onEditBeneficiaryClick = onEditBeneficiaryClick,
        onDeleteBeneficiaryClick = { beneficiary -> beneficiary.id.let { viewModel.deleteBeneficiary(it) } },
        modifier = modifier
    )
}
