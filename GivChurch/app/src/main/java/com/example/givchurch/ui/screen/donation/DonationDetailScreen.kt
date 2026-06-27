package com.example.givchurch.ui.screen.donation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.donation.DonationDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DonationDetailScreen(
    donationId: Int,
    onNavigateBack: () -> Unit,
    onEditDonationClick: (com.example.givchurch.domain.model.Donation) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DonationDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.activeDonationFlow.collectAsState()
    val operationState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = donationId) {
        viewModel.loadDonationDetails(donationId)
    }

    LaunchedEffect(key1 = operationState.isDeleteSuccess) {
        if (operationState.isDeleteSuccess) {
            viewModel.resetViewModel()
            onNavigateBack()
        }
    }

    DonationDetailContent(
        uiState = uiState.copy(
            isLoading = operationState.isLoading,
            errorMessage = operationState.errorMessage
        ),
        onNavigateBack = {
            viewModel.resetViewModel()
            onNavigateBack()
        },
        onEditClick = {
            uiState.donation?.let { currentDonation ->
                onEditDonationClick(currentDonation)
            }
        },
        onDeleteClick = {
            viewModel.deleteDonation()
        },
        onStatusChange = viewModel::updateDonationStatus,
        onClearError = viewModel::clearErrorMessage,
        modifier = modifier
    )
}
