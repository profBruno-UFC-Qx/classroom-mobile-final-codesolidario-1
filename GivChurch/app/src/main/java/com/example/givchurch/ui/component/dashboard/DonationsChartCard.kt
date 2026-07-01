package com.example.givchurch.ui.component.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.givchurch.domain.model.MonthlyDonation
import com.example.givchurch.ui.theme.GivChurchTheme

@Composable
fun DonationsChartCard(monthlyData: List<MonthlyDonation>) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "Doações nos últimos meses",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            DonationsBarChart(data = monthlyData)
        }
    }
}

@Preview(showBackground = true, name = "Card do Gráfico - Tema Claro")
@Composable
fun DonationsChartCardLightPreview() {
    GivChurchTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            DonationsChartCard(
                monthlyData = listOf(
                    MonthlyDonation("Jan", 30),
                    MonthlyDonation("Fev", 55),
                    MonthlyDonation("Mar", 85)
                )
            )
        }
    }
}
