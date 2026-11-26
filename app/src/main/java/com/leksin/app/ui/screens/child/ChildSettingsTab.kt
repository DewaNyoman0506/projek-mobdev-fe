package com.leksin.app.ui.screens.child

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leksin.app.R
import com.leksin.app.ui.components.LeksinPillButton
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

@Composable
fun ChildSettingsTab(
    onLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var musicVolume by remember { mutableFloatStateOf(0.7f) }
    var sfxVolume by remember { mutableFloatStateOf(0.9f) }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.gradien),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.xxl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header title card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        color = LeksinPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xxl))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, LeksinPrimary),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.xxl)
                ) {
                    Text(
                        text = "Music",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = LeksinPrimary
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Slider(
                        value = musicVolume,
                        onValueChange = { musicVolume = it },
                        colors = SliderDefaults.colors(
                            thumbColor = LeksinPrimary,
                            activeTrackColor = LeksinPrimary,
                            inactiveTrackColor = LeksinPrimary.copy(alpha = 0.3f)
                        )
                    )

                    Spacer(modifier = Modifier.height(Spacing.xl))

                    Text(
                        text = "SFX",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = LeksinPrimary
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Slider(
                        value = sfxVolume,
                        onValueChange = { sfxVolume = it },
                        colors = SliderDefaults.colors(
                            thumbColor = LeksinPrimary,
                            activeTrackColor = LeksinPrimary,
                            inactiveTrackColor = LeksinPrimary.copy(alpha = 0.3f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xxl))

            // Logout Button
            LeksinPillButton(
                text = "Keluar / Logout",
                modifier = Modifier.fillMaxWidth(),
                onClick = onLogoutClick
            )
        }
    }
}
