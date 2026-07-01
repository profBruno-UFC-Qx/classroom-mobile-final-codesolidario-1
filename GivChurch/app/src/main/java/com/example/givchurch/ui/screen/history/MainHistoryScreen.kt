package com.example.givchurch.ui.screen.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.history.MainHistoryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: MainHistoryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MainHistoryContent(
        uiState = uiState,
        loadBeneficiaryName = viewModel::loadBeneficiaryName,
        modifier = modifier
    )
}
