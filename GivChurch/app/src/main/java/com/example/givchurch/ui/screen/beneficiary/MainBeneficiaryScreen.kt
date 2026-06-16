package com.example.givchurch.ui.screen.beneficiary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.beneficiary.MainBeneficiaryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainBeneficiaryScreen(
    onAddBeneficiaryClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainBeneficiaryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MainBeneficiaryContent(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onAddBeneficiaryClick = onAddBeneficiaryClick,
        modifier = modifier
    )
}
