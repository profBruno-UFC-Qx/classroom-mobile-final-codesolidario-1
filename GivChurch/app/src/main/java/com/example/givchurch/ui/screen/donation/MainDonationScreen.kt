package com.example.givchurch.ui.screen.donation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.viewmodel.donation.MainDonationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainDonationScreen(
    onAddDonationClick: () -> Unit,
    onEditDonationClick: (Donation) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainDonationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MainDonationContent(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onCategorySelected = viewModel::onCategorySelected,
        onAddDonationClick = onAddDonationClick,
        onEditDonationClick = onEditDonationClick,
        onDeleteDonationClick = { donation -> viewModel.deleteDonation(donation.id) },
        onLoadBeneficiaryName = viewModel::loadBeneficiaryName,
        modifier = modifier
    )
}
