package com.leksin.app.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.leksin.app.ui.components.LeksinAuthContainer
import com.leksin.app.ui.components.LeksinHeaderTitle
import com.leksin.app.ui.components.LeksinPillButton
import com.leksin.app.ui.components.LeksinPillTextField
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

@Composable
fun ParentLoginScreen(
    onBackClick: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onLoginClick: (usernameOrEmail: String, password: String) -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Login
    LeksinAuthContainer(modifier = modifier) {
        LeksinHeaderTitle(
            title = "Log In",
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        // Register
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Anda belum memiliki akun? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Daftar",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = LeksinPrimary,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onNavigateToRegister
                )
            )
        }

        Spacer(modifier = Modifier.height(Spacing.xl))

        LeksinPillTextField(
            value = usernameOrEmail,
            onValueChange = { usernameOrEmail = it },
            placeholder = "Email",
            isError = errorMessage != null
        )

        Spacer(modifier = Modifier.height(Spacing.md))

        LeksinPillTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            isPassword = true,
            isError = errorMessage != null,
            supportingText = errorMessage
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.xs, end = Spacing.sm),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Lupa Password",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable { /* Handle forgot password */ }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        LeksinPillButton(
            text = if (isLoading) "Loading..." else "Masuk",
            enabled = !isLoading && usernameOrEmail.isNotBlank() && password.isNotBlank(),
            onClick = { onLoginClick(usernameOrEmail, password) }
        )
    }
}
