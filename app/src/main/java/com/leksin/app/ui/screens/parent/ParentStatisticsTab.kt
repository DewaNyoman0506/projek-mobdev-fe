package com.leksin.app.ui.screens.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.leksin.app.ui.components.LeksinStatCard
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

@Composable
fun ParentStatisticsTab(
    modifier: Modifier = Modifier
) {
    val levelProgress = listOf(
        Triple("Huruf Vokal", "8/8 stage", 1.0f),
        Triple("Huruf Konsonan", "6/8 stage", 0.75f),
        Triple("Suku Kata", "2/8 stage", 0.25f)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBEF))
            .verticalScroll(rememberScrollState())
            .padding(Spacing.xxl)
    ) {
        Text(
            text = "Statistik Belajar",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = LeksinPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LeksinStatCard(
                label = "Total Stage",
                value = "16",
                unit = "selesai",
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
            LeksinStatCard(
                label = "Streak Aktif",
                value = "5",
                unit = "hari",
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Level Progress Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Progres per Level",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = LeksinPrimary
                )

                levelProgress.forEach { (title, subtitle, progress) ->
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(50)),
                            color = LeksinPrimary,
                            trackColor = Color(0xFFF3EFE0)
                        )
                    }
                }
            }
        }
    }
}