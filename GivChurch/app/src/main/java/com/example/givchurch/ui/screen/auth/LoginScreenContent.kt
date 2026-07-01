package com.example.givchurch.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.givchurch.ui.component.AppHeader
import com.example.givchurch.ui.component.form.FormSectionLayout
import com.example.givchurch.ui.theme.GivChurchTheme
import com.example.givchurch.viewmodel.auth.LoginUiState
import com.example.givchurch.viewmodel.bible.BibleUiState

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    bibleUiState: BibleUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val verseText = when (bibleUiState) {
        is BibleUiState.Loading -> "Carregando palavra do dia..."
        is BibleUiState.Success -> "${bibleUiState.verse.text.trim()}\n— ${bibleUiState.verse.reference}"
        is BibleUiState.Error -> bibleUiState.fallbackVerse
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppHeader(
                    title = "Bem-vindo de volta",
                    subtitle = "Entre com as suas credenciais para continuar",
                    verse = verseText
                )

                FormSectionLayout(title = "E-mail") {
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = onEmailChange,
                        placeholder = { Text("Ex: maria@email.com") },
                        singleLine = true,
                        enabled = !uiState.isLoading,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                FormSectionLayout(title = "Senha") {
                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = onPasswordChange,
                        placeholder = { Text("Digite sua senha") },
                        singleLine = true,
                        enabled = !uiState.isLoading,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            val description = if (passwordVisible) "Ocultar senha" else "Mostrar senha"
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = description)
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Esqueceu a senha?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable(onClick = onForgotPasswordClick)
                    )
                }

                if (uiState.message.isNotBlank()) {
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onLoginClick,
                    enabled = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "Entrar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Não tem uma conta? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Cadastre-se",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(onClick = onCreateAccountClick)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Login - Oficial")
@Composable
fun LoginScreenPreview() {
    GivChurchTheme(darkTheme = false) {


        LoginScreenContent(
            uiState = LoginUiState(),
            bibleUiState = BibleUiState.Error("O Senhor é o meu pastor, nada me faltará. Salmos 23:1"),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onCreateAccountClick = {},
            onForgotPasswordClick = {}
        )
    }
}