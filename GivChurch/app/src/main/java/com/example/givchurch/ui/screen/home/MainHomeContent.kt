package com.example.givchurch.ui.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.givchurch.domain.model.DashboardMetrics
import com.example.givchurch.domain.model.MonthlyDonation
import com.example.givchurch.ui.component.dashboard.DashboardContent
import com.example.givchurch.ui.theme.GivChurchTheme
import com.example.givchurch.viewmodel.home.DashboardUiState

@Composable
fun MainHomeContent(
    uiState: DashboardUiState,
    modifier: Modifier = Modifier
) {
    val currentUserName = if (uiState is DashboardUiState.Success) uiState.userName else ""

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Olá,",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp
                    )
                    AnimatedContent(
                        targetState = currentUserName,
                        transitionSpec = {
                            fadeIn().togetherWith(fadeOut())
                        },
                        label = "NomeTransicao"
                    ) { targetName ->
                        Text(
                            text = targetName,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Continue fazendo a diferença na comunidade",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
        when (val state = uiState) {
            is DashboardUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            is DashboardUiState.Success -> {
                DashboardContent(
                    metrics = state.metrics,
                    monthlyData = state.monthlyDonations,
                    recentDonations = state.recentDonations
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Dashboard Principal - Oficial")
@Composable
fun MainHomeScreenPreview() {
    GivChurchTheme(darkTheme = false) {
        MainHomeContent(
            uiState = DashboardUiState.Success(
                userName = "Maria",
                metrics = DashboardMetrics(
                    totalDonations = 120,
                    pendingDonations = 45,
                    deliveredDonations = 75,
                    totalBeneficiaries = 18
                ),
                monthlyDonations = listOf(
                    MonthlyDonation("Jan", 30),
                    MonthlyDonation("Fev", 55),
                    MonthlyDonation("Mar", 85)
                ),
                recentDonations = emptyList()
            )
        )
    }
}
