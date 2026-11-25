package com.leksin.app.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
fun ParentRegisterScreen(
    onBackClick: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onRegisterClick: (username: String, email: String, password: String) -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordMismatchError by remember { mutableStateOf<String?>(null) }

    LeksinAuthContainer(modifier = modifier) {
        LeksinHeaderTitle(
            title = "Sign Up",
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Anda sudah memiliki akun? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Masuk",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = LeksinPrimary,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onNavigateToLogin
                )
            )
        }

        Spacer(modifier = Modifier.height(Spacing.xl))

        LeksinPillTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = "Username"
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        LeksinPillTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Email"
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        LeksinPillTextField(
            value = password,
            onValueChange = {
                password = it
                if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                    passwordMismatchError = "Password tidak cocok"
                } else {
                    passwordMismatchError = null
                }
            },
            placeholder = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        LeksinPillTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                if (password != confirmPassword) {
                    passwordMismatchError = "Password tidak cocok"
                } else {
                    passwordMismatchError = null
                }
            },
            placeholder = "Konfirmasi Password",
            isPassword = true,
            isError = passwordMismatchError != null || errorMessage != null,
            supportingText = passwordMismatchError ?: errorMessage
        )

        Spacer(modifier = Modifier.height(28.dp))

        LeksinPillButton(
            text = if (isLoading) "Loading..." else "Daftar",
            enabled = !isLoading && username.isNotBlank() && email.isNotBlank() &&
                    password.isNotBlank() && password == confirmPassword,
            onClick = { onRegisterClick(username, email, password) }
        )
    }
}
