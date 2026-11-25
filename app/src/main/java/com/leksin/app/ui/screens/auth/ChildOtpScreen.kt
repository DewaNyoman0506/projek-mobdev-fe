package com.leksin.app.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.leksin.app.ui.components.LeksinOtpField
import com.leksin.app.ui.components.LeksinPillButton
import com.leksin.app.ui.components.LeksinPillTextField
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

@Composable
fun ChildOtpScreen(
    onBackClick: () -> Unit,
    onRequestOtpClick: (parentUsername: String, nickname: String) -> Unit,
    onVerifyOtpClick: (parentUsername: String, nickname: String, code: String) -> Unit,
    isLoading: Boolean = false,
    otpSent: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    var parentUsername by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }

    LeksinAuthContainer(modifier = modifier) {
        LeksinHeaderTitle(
            title = "Masukkan Kode OTP",
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(Spacing.xs))

        Text(
            text = if (otpSent)
                "Kode OTP telah berhasil dikirimkan menuju email orang tua anda."
            else
                "Masukkan username Orang Tua & Nama Anak untuk menerima kode OTP.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Spacing.xl))

        if (!otpSent) {
            // Nickname/name parent
            LeksinPillTextField(
                value = parentUsername,
                onValueChange = { parentUsername = it },
                placeholder = "Username Orang Tua"
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            LeksinPillTextField(
                value = nickname,
                onValueChange = { nickname = it },
                placeholder = "Nama Anak (Nickname)",
                isError = errorMessage != null,
                supportingText = errorMessage
            )

            Spacer(modifier = Modifier.height(28.dp))

            LeksinPillButton(
                text = if (isLoading) "Minta OTP..." else "Minta OTP",
                enabled = !isLoading && parentUsername.isNotBlank() && nickname.isNotBlank(),
                onClick = { onRequestOtpClick(parentUsername, nickname) }
            )
        } else {
            // OTP
            LeksinOtpField(
                value = otpCode,
                onValueChange = { otpCode = it },
                modifier = Modifier.fillMaxWidth(),
                length = 6,
                isError = errorMessage != null,
                supportingText = errorMessage
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tidak menerima kode OTP? ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Kirim Ulang",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = LeksinPrimary,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onRequestOtpClick(parentUsername, nickname) }
                    )
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            LeksinPillButton(
                text = if (isLoading) "Verifikasi..." else "Submit",
                enabled = !isLoading && otpCode.length == 6,
                onClick = { onVerifyOtpClick(parentUsername, nickname, otpCode) }
            )
        }
    }
}
