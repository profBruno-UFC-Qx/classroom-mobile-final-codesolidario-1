package com.example.givchurch.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.givchurch.viewmodel.home.MainHomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainHomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MainHomeContent(
        uiState = uiState,
        modifier = modifier
    )
}
