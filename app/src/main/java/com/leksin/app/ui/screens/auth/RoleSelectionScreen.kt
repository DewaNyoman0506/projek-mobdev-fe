package com.leksin.app.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leksin.app.ui.components.LeksinAuthContainer
import com.leksin.app.ui.components.LeksinPillButton
import com.leksin.app.ui.components.LeksinRoleOptionButton
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

enum class UserRole { PARENT, CHILD }

@Composable
fun RoleSelectionScreen(
    onRoleSelected: (UserRole) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRole by remember { mutableStateOf(UserRole.PARENT) }

    LeksinAuthContainer(modifier = modifier) {
        Text(
            text = "Memilih Peran",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            ),
            color = LeksinPrimary
        )

        Spacer(modifier = Modifier.height(Spacing.xxl))

        // Ortu / Anak momen

        Column {
            LeksinRoleOptionButton(
                text = "Orang Tua",
                isSelected = selectedRole == UserRole.PARENT,
                onClick = { selectedRole = UserRole.PARENT }
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            LeksinRoleOptionButton(
                text = "Anak",
                isSelected = selectedRole == UserRole.CHILD,
                onClick = { selectedRole = UserRole.CHILD }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        LeksinPillButton(
            text = "Pilih Peran",
            onClick = { onRoleSelected(selectedRole) }
        )
    }
}
