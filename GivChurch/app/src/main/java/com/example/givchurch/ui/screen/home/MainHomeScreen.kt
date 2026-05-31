package com.example.givchurch.ui.screen.home

import android.graphics.Paint
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.ContentPasteSearch
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.givchurch.data.mock.BeneficiaryMockData
import com.example.givchurch.data.model.Donation
import com.example.givchurch.data.model.enums.DonationStatus
import com.example.givchurch.data.repository.DashboardMetrics
import com.example.givchurch.data.repository.MonthlyDonation
import com.example.givchurch.viewmodel.home.DashboardUiState
import com.example.givchurch.viewmodel.home.MainHomeViewModel
import java.time.format.DateTimeFormatter

@Composable
fun MainHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainHomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
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
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Voluntário",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Continue fazendo a diferença na comunidade",
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
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
                    CircularProgressIndicator()
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
                label = "Beneficiários"
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

@Composable
fun MetricCard(
    icon: ImageVector,
    value: String,
    label: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = label,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun RecentDonationCard(
    donation: Donation,
    beneficiaryName: String
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = donation.createdAt.format(dateFormatter)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = donation.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = beneficiaryName,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
                Text(
                    text = formattedDate,


                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
            val (statusText, statusBg, statusTextCol) = when (donation.status) {
                DonationStatus.DELIVERED -> Triple("Entregue", Color(0xFFE8F5E9), Color(0xFF2E7D32))
                DonationStatus.PENDING -> Triple("Pendente", Color(0xFFFFF8E1), Color(0xFFF57F17))
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(statusBg)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = statusText,
                    color = statusTextCol,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
@Composable
fun DonationsChartCard(monthlyData: List<MonthlyDonation>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
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
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Doações nos últimos meses",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            DonationsBarChart(data = monthlyData)
        }
    }
}
@Composable
fun DonationsBarChart(data: List<MonthlyDonation>) {
    val barColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurfaceVariant.toArgb()
    val maxVal = (data.maxOfOrNull { it.totalAmount } ?: 0).coerceAtLeast(80)

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val axisOffsetLeft = 80f
        val axisOffsetBottom = 60f

        val chartWidth = canvasWidth - axisOffsetLeft
        val chartHeight = canvasHeight - axisOffsetBottom

        drawLine(
            color = Color.Gray.copy(alpha = 0.5f),
            start = Offset(axisOffsetLeft, 0f),
            end = Offset(axisOffsetLeft, chartHeight),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.Gray.copy(alpha = 0.5f),
            start = Offset(axisOffsetLeft, chartHeight),
            end = Offset(canvasWidth, chartHeight),
            strokeWidth = 2f
        )

        val ySteps = 4
        for (i in 0..ySteps) {
            val yValue = (maxVal / ySteps) * i
            val yPos = chartHeight - (chartHeight / ySteps) * i

            drawContext.canvas.nativeCanvas.drawText(
                yValue.toString(),
                axisOffsetLeft - 20f,
                yPos + 10f,
                Paint().apply {
                    color = textColor
                    textSize = 32f
                    textAlign = Paint.Align.RIGHT
                }
            )
        }

        if (data.isNotEmpty()) {
            val barWidth = (chartWidth / data.size) * 0.6f
            val spaceWidth = (chartWidth / data.size) * 0.4f

            data.forEachIndexed { index, item ->
                val barHeight = if (maxVal > 0) (item.totalAmount.toFloat() / maxVal) * chartHeight else 0f
                val xPos = axisOffsetLeft + (index * (barWidth + spaceWidth)) + (spaceWidth / 2)
                val yPos = chartHeight - barHeight

                drawRoundRect(
                    color = barColor,
                    topLeft = Offset(xPos, yPos),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(12f, 12f)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    item.monthName,
                    xPos + (barWidth / 2),
                    chartHeight + 40f,
                    Paint().apply {
                        color = textColor
                        textSize = 32f
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainHomeScreenPreview() {
    MaterialTheme {
        MainHomeScreen()
    }
}