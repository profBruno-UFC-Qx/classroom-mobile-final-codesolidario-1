package com.example.givchurch.ui.component.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.givchurch.ui.theme.BackgroundLight
import com.example.givchurch.ui.theme.GivChurchTheme

@Composable
fun CardClickableOption(
    icon: ImageVector,
    title: String,
    tintColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    val isErrorColor = tintColor == MaterialTheme.colorScheme.error

    val borderColor = if (isErrorColor) {
        MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.7f)
    }

    val backgroundColor = if (isErrorColor) {
        MaterialTheme.colorScheme.error.copy(alpha = 0.06f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(1.2.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tintColor
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = tintColor
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = tintColor.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(showBackground = true, name = "Opções do Perfil - Igual a Imagem")
@Composable
fun CardClickableOptionLightPreview() {
    GivChurchTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(BackgroundLight)
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CardClickableOption(
                    icon = Icons.Default.HelpOutline,
                    title = "Ajuda",
                    onClick = {}
                )
                CardClickableOption(
                    icon = Icons.Default.Person,
                    title = "Sair da conta",
                    tintColor = MaterialTheme.colorScheme.error,
                    onClick = {}
                )
            }
        }
    }
}
