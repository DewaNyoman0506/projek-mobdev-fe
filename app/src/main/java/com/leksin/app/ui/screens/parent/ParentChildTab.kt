package com.leksin.app.ui.screens.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.leksin.app.ui.components.LeksinButton
import com.leksin.app.ui.components.LeksinTextField
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

private data class ChildItem(
    val name: String,
    val otpCode: String,
    val dailyTarget: Int
)

@Composable
fun ParentChildTab(
    modifier: Modifier = Modifier
) {
    val children = remember {
        mutableStateListOf(
            ChildItem(name = "Anak 1", otpCode = "4821", dailyTarget = 8)
        )
    }
    var newChildName by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBEF))
            .verticalScroll(rememberScrollState())
            .padding(Spacing.xxl),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Daftar Anak",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = LeksinPrimary
        )

        // Child Profile & OTP Cards
        children.forEach { child ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = child.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = LeksinPrimary
                        )
                        Text(
                            text = "Target harian: ${child.dailyTarget} stage",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    ) {
                        Text(
                            text = "OTP: ${child.otpCode}",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Add New Child Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Tambah Anak",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = LeksinPrimary
                )

                LeksinTextField(
                    value = newChildName,
                    onValueChange = { newChildName = it },
                    label = "Nama Anak"
                )

                LeksinButton(
                    text = "Simpan",
                    onClick = {
                        if (newChildName.isNotBlank()) {
                            val randomOtp = (1000..9999).random().toString()
                            children.add(
                                ChildItem(
                                    name = newChildName.trim(),
                                    otpCode = randomOtp,
                                    dailyTarget = 8
                                )
                            )
                            newChildName = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}