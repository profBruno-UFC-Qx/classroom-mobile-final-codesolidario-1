package com.example.givchurch.ui.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.givchurch.ui.theme.BackgroundLight
import com.example.givchurch.ui.theme.GivChurchTheme

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
    )
}

@Preview(showBackground = true, name = "Título da Seção - Oficial")
@Composable
fun SectionTitlePreview() {
    GivChurchTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(BackgroundLight)
                .padding(16.dp)
        ) {
            SectionTitle(title = "CONFIGURAÇÕES")
        }
    }
}
