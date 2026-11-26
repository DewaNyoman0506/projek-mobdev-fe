package com.leksin.app.ui.screens.child

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leksin.app.R
import com.leksin.app.ui.components.BackArrowIcon
import com.leksin.app.ui.components.LeksinPillButton
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

/**
 * Reusable stage configuration template for levels A, B, C, D, etc.
 */
data class StageGameConfig(
    val stageId: String,
    val targetWord: String,
    val targetImageRes: Int,
    val scrambledChars: List<Char>
)

fun getStageConfig(stageId: String): StageGameConfig {

    // KALO MAU NAMBAH STAGE DISINI!

    return when (stageId.uppercase()) {
        "A" -> StageGameConfig(
            stageId = "A",
            targetWord = "APEL",
            targetImageRes = R.drawable.apel,
            scrambledChars = listOf('P', 'E', 'A', 'L', 'S', 'T')
        )
        "B" -> StageGameConfig(
            stageId = "B",
            targetWord = "BUKU",
            targetImageRes = R.drawable.buku,
            scrambledChars = listOf('B', 'U', 'K', 'U', 'S', 'I')
        )
        "C" -> StageGameConfig(
            stageId = "C",
            targetWord = "CUMI",
            targetImageRes = R.drawable.cumi,
            scrambledChars = listOf('C', 'U', 'M', 'I', 'S', 'A')
        )
        "D" -> StageGameConfig(
            stageId = "D",
            targetWord = "DOMBA",
            targetImageRes = R.drawable.domba,
            scrambledChars = listOf('D', 'O', 'M', 'B', 'A', 'S')
        )
        else -> StageGameConfig(
            stageId = stageId,
            targetWord = "APEL",
            targetImageRes = R.drawable.apel,
            scrambledChars = listOf('P', 'E', 'A', 'L', 'S', 'T')
        )
    }
}

data class LetterTileModel(
    val id: Int,
    val char: Char,
    var isUsed: Boolean = false
)

@Composable
fun ChildGameStageScreen(
    stageId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    // Load config template dynamically based on stageId (A, B, C, D)
    val config = remember(stageId) { getStageConfig(stageId) }
    val targetWord = config.targetWord

    // Adaptive sizing based on word length / letter count
    val slotSize = when {
        targetWord.length >= 6 -> 42.dp
        targetWord.length >= 5 -> 48.dp
        else -> 54.dp
    }
    val slotFontSize = when {
        targetWord.length >= 6 -> 20.sp
        targetWord.length >= 5 -> 24.sp
        else -> 28.sp
    }

    val tileSize = if (config.scrambledChars.size > 6) 44.dp else 50.dp
    val tileFontSize = if (config.scrambledChars.size > 6) 18.sp else 22.sp

    var hearts by remember { mutableIntStateOf(3) }
    val slots = remember(stageId) {
        androidx.compose.runtime.mutableStateListOf<Char?>().apply {
            repeat(targetWord.length) { add(null) }
        }
    }
    val tiles = remember(stageId) {
        androidx.compose.runtime.mutableStateListOf<LetterTileModel>().apply {
            config.scrambledChars.forEachIndexed { index, c ->
                add(LetterTileModel(index, c))
            }
        }
    }

    var showWinDialog by remember { mutableStateOf(false) }
    var showGameOverDialog by remember { mutableStateOf(false) }
    var feedbackMessage by remember { mutableStateOf<String?>(null) }

    // Anim awan
    val infiniteTransition = rememberInfiniteTransition(label = "cloudFloat")
    val cloudOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = androidx.compose.animation.core.FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cloud1"
    )
    val cloudOffset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = androidx.compose.animation.core.FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cloud2"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Gradient Background
        Image(
            painter = painterResource(id = R.drawable.gradien),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Decorative Sun
        Image(
            painter = painterResource(id = R.drawable.sun),
            contentDescription = "Sun",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(240.dp)
                .offset(x = 50.dp, y = (-95).dp),
            contentScale = ContentScale.Fit
        )

        // Background Cloud (awan1)
        Image(
            painter = painterResource(id = R.drawable.awan1),
            contentDescription = "Cloud Back",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .offset(y = cloudOffset1.dp),
            contentScale = ContentScale.FillWidth
        )

        // Foreground Cloud (awan2)
        Image(
            painter = painterResource(id = R.drawable.awan2),
            contentDescription = "Cloud Front",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .offset(y = cloudOffset2.dp),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.xxl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back button + hearts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { index ->
                        val isAlive = index < hearts
                        Image(
                            painter = painterResource(
                                id = if (isAlive) R.drawable.heart else R.drawable.takenheart
                            ),
                            contentDescription = "Heart $index",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xl))

            // Target Image (Per stage config)
            Surface(
                modifier = Modifier.size(150.dp),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = config.targetImageRes),
                        contentDescription = "Target Image for Stage ${config.stageId}",
                        modifier = Modifier.size(110.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xl))

            // Answer Slots (Adaptive semoga aja gak break)
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                modifier = Modifier.padding(vertical = Spacing.md)
            ) {
                slots.forEachIndexed { index, char ->
                    Surface(
                        modifier = Modifier.size(slotSize),
                        shape = RoundedCornerShape(12.dp),
                        color = if (char != null) LeksinPrimary else Color(0xFFD3D3E0),
                        shadowElevation = 2.dp
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(enabled = char != null) {
                                    if (char != null) {
                                        val tile = tiles.firstOrNull { it.char == char && it.isUsed }
                                        if (tile != null) {
                                            tile.isUsed = false
                                        }
                                        slots[index] = null
                                    }
                                }
                        ) {
                            if (char != null) {
                                Text(
                                    text = char.toString(),
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = slotFontSize
                                    ),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            if (feedbackMessage != null) {
                Text(
                    text = feedbackMessage!!,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(Spacing.md))

            // Pool box kata (Pake adaptive spacing)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFFFCE1A3).copy(alpha = 0.9f),
                shadowElevation = 2.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val chunks = tiles.chunked(3)
                        chunks.forEach { rowTiles ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                            ) {
                                rowTiles.forEach { tile ->
                                    LetterTileItem(
                                        tile = tile,
                                        tileSize = tileSize,
                                        fontSize = tileFontSize,
                                        onTap = {
                                            if (!tile.isUsed) {
                                                val emptyIndex = slots.indexOf(null)
                                                if (emptyIndex != -1) {
                                                    slots[emptyIndex] = tile.char
                                                    tile.isUsed = true
                                                    feedbackMessage = null
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // JAWAB! Button
            LeksinPillButton(
                text = "JAWAB!",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val currentWord = slots.joinToString("") { it?.toString().orEmpty() }
                    if (currentWord.length < targetWord.length) {
                        feedbackMessage = "Lengkapi semua huruf!"
                        return@LeksinPillButton
                    }

                    if (currentWord.equals(targetWord, ignoreCase = true)) {
                        CatCollectionPrefs.unlockStage(context, config.stageId)
                        StreakManager.recordDailyActivity(context)
                        showWinDialog = true
                    } else {
                        hearts--
                        if (hearts <= 0) {
                            showGameOverDialog = true
                        } else {
                            feedbackMessage = "Ups, masih salah! Coba lagi."
                            slots.fill(null)
                            tiles.forEach { it.isUsed = false }
                        }
                    }
                }
            )
        }

        // WIN
        if (showWinDialog) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Hore! Benar 🎉") },
                text = { Text("Hebat! Kamu berhasil menebak kata $targetWord dengan benar di Stage ${config.stageId}.") },
                confirmButton = {
                    TextButton(onClick = {
                        showWinDialog = false
                        onBackClick()
                    }) {
                        Text("Kembali ke Peta")
                    }
                }
            )
        }

        // Game Over
        if (showGameOverDialog) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Game Over 💔") },
                text = { Text("Nyawa kamu habis! Jangan menyerah, ayo coba lagi.") },
                confirmButton = {
                    TextButton(onClick = {
                        showGameOverDialog = false
                        hearts = 3
                        slots.fill(null)
                        tiles.forEach { it.isUsed = false }
                        feedbackMessage = null
                    }) {
                        Text("Coba Lagi")
                    }
                }
            )
        }
    }
}

@Composable
fun LetterTileItem(
    tile: LetterTileModel,
    tileSize: Dp,
    fontSize: TextUnit,
    onTap: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(tileSize)
            .clickable(enabled = !tile.isUsed, onClick = onTap),
        shape = RoundedCornerShape(14.dp),
        color = if (tile.isUsed) Color(0xFFE0E0E0) else Color.White,
        shadowElevation = 2.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = tile.char.toString(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize
                ),
                color = if (tile.isUsed) Color.Gray else LeksinPrimary
            )
        }
    }
}
