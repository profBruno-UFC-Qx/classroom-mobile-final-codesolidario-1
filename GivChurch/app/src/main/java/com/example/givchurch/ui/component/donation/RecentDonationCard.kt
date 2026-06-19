package com.example.givchurch.ui.component.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.ui.theme.GivChurchTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun RecentDonationCard(
    donation: Donation,
    beneficiaryName: String,
    modifier: Modifier = Modifier
) {
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }
    val formattedDate = remember(donation.createdAt) { donation.createdAt.format(dateFormatter) }

    val statusColor = when (donation.status) {
        DonationStatus.DELIVERED -> MaterialTheme.colorScheme.onPrimaryContainer
        DonationStatus.PENDING -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.outline
    }

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
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
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = beneficiaryName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = statusColor.copy(alpha = 0.15f),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = donation.status.value,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = statusColor
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Card Doação Recente Oficial")
@Composable
fun RecentDonationCardPreview() {
    GivChurchTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            RecentDonationCard(
                donation = Donation(
                    id = 2,
                    imageUrl = "",
                    name = "Distribuição de roupas",
                    category = DonationCategory.CLOTHING,
                    description = "Distribuição de roupas para a comunidade X",
                    quantity = 1,
                    beneficiaryId = 2,
                    createBy = "d1e8f2c3-a4b5-4c6d-9e8f-7a6b5c4d3e2f",
                    status = DonationStatus.PENDING,
                    createdAt = LocalDateTime.now(),
                    dueDate = LocalDateTime.now().plusDays(2)
                ),
                beneficiaryName = "João Santos"
            )
        }
    }
}
