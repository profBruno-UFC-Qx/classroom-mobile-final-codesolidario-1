package com.example.givchurch.ui.screen.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.givchurch.ui.component.profile.CardClickableOption
import com.example.givchurch.ui.component.profile.CardOptionWithSwitch
import com.example.givchurch.ui.component.profile.SectionTitle
import com.example.givchurch.ui.theme.GivChurchTheme
import com.example.givchurch.viewmodel.profile.ProfileUiState

@Composable
fun MainProfileContent(
    uiState: ProfileUiState,
    onThemeToggle: (Boolean) -> Unit,
    onNotificationsToggle: (Boolean) -> Unit,
    onHelpClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Column {
                Text(
                    text = "Perfil",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.imageUrl.isBlank()) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Foto de perfil padrão",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        } else {
                            AsyncImage(
                                model = uiState.imageUrl,
                                contentDescription = "Foto de perfil do usuário",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        AnimatedContent(
                            targetState = uiState.userName,
                            transitionSpec = { fadeIn().togetherWith(fadeOut()) },
                            label = "NomePerfilTransicao"
                        ) { name ->
                            Text(
                                text = name,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        AnimatedContent(
                            targetState = uiState.userEmail,
                            transitionSpec = { fadeIn().togetherWith(fadeOut()) },
                            label = "EmailPerfilTransicao"
                        ) { email ->
                            Text(
                                text = email,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }

                        TextButton(
                            onClick = onEditProfileClick,
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.height(24.dp)
                        ) {
                            Text(
                                text = "Editar perfil",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle(title = "CONFIGURAÇÕES")

            CardOptionWithSwitch(
                icon = Icons.Default.WbSunny,
                title = "Tema Escuro",
                checked = !uiState.isLightTheme,
                onCheckedChange = onThemeToggle
            )

            CardOptionWithSwitch(
                icon = Icons.Default.Notifications,
                title = "Notificações",
                checked = uiState.isNotificationsEnabled,
                onCheckedChange = onNotificationsToggle
            )

            Spacer(modifier = Modifier.height(8.dp))

            SectionTitle(title = "INFORMAÇÕES")

            CardClickableOption(
                icon = Icons.Default.HelpOutline,
                title = "Ajuda",
                onClick = onHelpClick
            )

            CardClickableOption(
                icon = Icons.Default.PrivacyTip,
                title = "Política de Privacidade",
                onClick = onPrivacyPolicyClick
            )

            CardClickableOption(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                title = "Sair da conta",
                tintColor = MaterialTheme.colorScheme.error,
                onClick = onLogoutClick
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Perfil Principal - Oficial")
@Composable
fun MainProfileScreenPreview() {
    GivChurchTheme(darkTheme = false) {
        MainProfileContent(
            uiState = ProfileUiState(
                userName = "Rubens Rabelo",
                userEmail = "rubens@email.com",
                isLightTheme = false
            ),
            onThemeToggle = {},
            onNotificationsToggle = {},
            onHelpClick = {},
            onPrivacyPolicyClick = {},
            onLogoutClick = {},
            onEditProfileClick = {}
        )
    }
}
