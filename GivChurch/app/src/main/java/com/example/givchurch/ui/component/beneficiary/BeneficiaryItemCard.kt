package com.example.givchurch.ui.component.beneficiary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.ui.theme.GivChurchTheme

@Composable
fun BeneficiaryItemCard(
    beneficiary: Beneficiary,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedPhone = formatDisplayPhone(beneficiary.phoneNumber)

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = beneficiary.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = formattedPhone,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = beneficiary.address,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (beneficiary.observations.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Obs: ${beneficiary.observations}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Excluir")
                }

                Spacer(modifier = Modifier.width(4.dp))

                TextButton(
                    onClick = onEditClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar")
                }
            }
        }
    }
}

private fun formatDisplayPhone(phone: String): String {
    val digits = phone.filter { it.isDigit() }
    if (digits.length != 11) return phone
    return "(${digits.substring(0, 2)}) ${digits.substring(2, 3)} ${digits.substring(3, 7)}-${digits.substring(7)}"
}

@Preview(showBackground = true, name = "Card Atendido Oficial")
@Composable
fun BeneficiaryItemCardPreview() {
    GivChurchTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            BeneficiaryItemCard(
                beneficiary = Beneficiary(
                    id = 1,
                    name = "Maria Silva",
                    phoneNumber = "85999991234",
                    address = "Rua das Flores, 123 - Centro",
                    observations = "Necessita de acompanhamento mensal e fraldas.",
                    createBy = "d1e8f2c3-a4b5-4c6d-9e8f-7a6b5c4d3e2f"
                ),
                onEditClick = {},
                onDeleteClick = {}
            )
        }
    }
}
