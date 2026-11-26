package com.leksin.app.ui.screens.child

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leksin.app.R
import com.leksin.app.ui.components.BackArrowIcon
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

data class LevelStage(
    val id: String,
    val label: String,
    val isUnlocked: Boolean,
    val xOffsetFraction: Float, // -0.8f to 0.8f for zigzag
    val yFraction: Float        // 0.0f (top) to 1.0f (bottom)
)

@Composable
fun ChildLevelSelectionScreen(
    onBackClick: () -> Unit,
    onStageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val stages = listOf(
        LevelStage("A", "A", true, -0.45f, 0.82f),  // Bottom Left
        LevelStage("B", "B", true, 0.45f, 0.64f),   // Lower-Middle Right
        LevelStage("C", "C", true, -0.45f, 0.46f),  // Middle Left
        LevelStage("D", "D", true, 0.45f, 0.28f),   // Upper-Middle Right
        LevelStage("E", "E", false, -0.45f, 0.10f) // Top Left (Future Content - Locked)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBEF))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFFDFBEF))
                .padding(horizontal = Spacing.xxl, vertical = Spacing.lg),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, LeksinPrimary, CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onBackClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                BackArrowIcon(
                    tint = LeksinPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // MAP
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFD5E952))
        ) {
            // STage di zig-zagin
            stages.forEach { stage ->
                PodiumNode(
                    stage = stage,
                    onClick = {
                        if (stage.isUnlocked) {
                            onStageSelected(stage.id)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun PodiumNode(
    stage: LevelStage,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val xDp = (stage.xOffsetFraction * 140).dp
    val yDp = ((stage.yFraction - 0.5f) * 580).dp

    Box(
        modifier = modifier
            .offset(x = xDp, y = yDp)
            .size(96.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        // Bawahnya, whatever itu namany.
        Image(
            painter = painterResource(id = if (stage.isUnlocked) R.drawable.thing else R.drawable.thinglocked),
            contentDescription = "Stage ${stage.label}",
            modifier = Modifier.fillMaxSize()
        )

        // Letter stagenya
        Text(
            text = stage.label,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Black,
                fontSize = 36.sp
            ),
            color = if (stage.isUnlocked) Color.White else Color.DarkGray,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-8).dp)
        )
    }
}
