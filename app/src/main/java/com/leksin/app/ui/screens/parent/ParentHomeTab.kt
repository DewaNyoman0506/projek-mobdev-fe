package com.leksin.app.ui.screens.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leksin.app.ui.components.LeksinStatCard
import com.leksin.app.ui.components.MeanStarCard
import com.leksin.app.ui.components.MeanTryCard
import com.leksin.app.ui.components.PlayTimeCard
import com.leksin.app.ui.components.StageCard
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.LeksinTheme
import com.leksin.app.ui.theme.Spacing

@Preview(showBackground = true)
@Composable
fun ParentHomePreview() {
    LeksinTheme {
        ParentHomeTab()
    }
}

@Composable
fun ParentHomeTab(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBEF))
            .padding(Spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LeksinStatCard(
            label = "75% target harian tercapai!",
            value = "test",
            unit = "juga",
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Spacing.xl))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            PlayTimeCard(
                minutes = 40,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
            StageCard(
                done = 6,
                total = 8,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier = Modifier.height(Spacing.xl))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            MeanStarCard(
                mean = 2.8,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
            MeanTryCard(
                mean = 1.2,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier = Modifier.height(Spacing.xl))
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Anak anda hari ini menyelesaikan 6 stage, dan bermain selama 40 menit. Stage yang diselesaikan masih di bawah dari target yang diharapkan.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = LeksinPrimary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}