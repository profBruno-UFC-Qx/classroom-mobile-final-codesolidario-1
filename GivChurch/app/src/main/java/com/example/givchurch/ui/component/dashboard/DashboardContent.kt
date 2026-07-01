package com.example.givchurch.ui.component.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.ContentPasteSearch
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.givchurch.data.mock.BeneficiaryMockData
import com.example.givchurch.domain.model.DashboardMetrics
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.MonthlyDonation
import com.example.givchurch.ui.component.donation.RecentDonationCard
import com.example.givchurch.ui.theme.GivChurchTheme

@Composable
fun DashboardContent(
    metrics: DashboardMetrics,
    monthlyData: List<MonthlyDonation>,
    recentDonations: List<Donation>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            MetricCard(
                icon = Icons.Default.Inventory2,
                value = metrics.totalDonations.toString(),
                label = "Total"
            )
        }
        item {
            MetricCard(
                icon = Icons.Default.ContentPasteSearch,
                value = metrics.pendingDonations.toString(),
                label = "Pendentes"
            )
        }
        item {
            MetricCard(
                icon = Icons.Default.CheckCircleOutline,
                value = metrics.deliveredDonations.toString(),
                label = "Entregues"
            )
        }
        item {
            MetricCard(
                icon = Icons.Default.Group,
                value = metrics.totalBeneficiaries.toString(),
                label = "Atendidos"
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            DonationsChartCard(monthlyData = monthlyData)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Doações Recentes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        items(recentDonations, key = { it.id }, span = { GridItemSpan(maxLineSpan) }) { donation ->
            val beneficiaryName = BeneficiaryMockData.beneficiaries
                .find { it.id == donation.beneficiaryId }?.name ?: "Beneficiário"
            RecentDonationCard(
                donation = donation,
                beneficiaryName = beneficiaryName
            )
        }
    }
}

@Preview(showBackground = true, name = "Dashboard Content - Tema Claro")
@Composable
fun DashboardContentLightPreview() {
    GivChurchTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            DashboardContent(
                metrics = DashboardMetrics(
                    totalDonations = 120,
                    pendingDonations = 45,
                    deliveredDonations = 75,
                    totalBeneficiaries = 18
                ),
                monthlyData = listOf(
                    MonthlyDonation("Jan", 30),
                    MonthlyDonation("Fev", 55),
                    MonthlyDonation("Mar", 85)
                ),
                recentDonations = emptyList()
            )
        }
    }
}
