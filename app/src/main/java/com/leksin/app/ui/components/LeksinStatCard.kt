package com.leksin.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.leksin.app.ui.theme.LeksinCardShape
import com.leksin.app.ui.theme.Spacing

// Contoh stat ortu

@Composable
fun LeksinStatCard(
    label: String,
    value: String,
    unit: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier, shape = LeksinCardShape, color = containerColor, contentColor = contentColor) {
        Column(Modifier.padding(Spacing.xxl)) {
            Text(label, style = MaterialTheme.typography.titleSmall)
            Text(value, style = MaterialTheme.typography.displayMedium)
            Text(unit, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun PlayTimeCard(minutes: Int, modifier: Modifier = Modifier) = LeksinStatCard(
    label = "Waktu bermain", value = minutes.toString(), unit = "menit",
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.onPrimary,
    modifier = modifier,
)

@Composable
fun StageCard(done: Int, total: Int, modifier: Modifier = Modifier) = LeksinStatCard(
    label = "Stage selesai", value = "$done", unit = "dari $total stage",
    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
    modifier = modifier,
)

@Composable
fun MeanStarCard(mean: Double, modifier: Modifier = Modifier) = LeksinStatCard(
    label = "Rata-rata Bintang", value = "$mean", unit = "",
    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
    modifier = modifier,
)

@Composable
fun MeanTryCard(mean: Double, modifier: Modifier = Modifier) = LeksinStatCard(
    label = "Rata-rata Percobaan", value = "$mean x", unit = "",
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.onPrimary,
    modifier = modifier,
)
