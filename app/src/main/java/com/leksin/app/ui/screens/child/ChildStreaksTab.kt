package com.leksin.app.ui.screens.child

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leksin.app.R
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getDrawableByName(context: Context, name: String): Int {
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}

@Composable
fun ChildStreaksTab(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Remember streak
    val currentStreak = remember { StreakManager.getCurrentStreak(context) }
    val longestStreak = remember { StreakManager.getLongestStreak(context) }
    val streakDates = remember { StreakManager.getStreakDates(context) }

    // Bulan
    var displayedCalendar by remember { mutableStateOf(Calendar.getInstance()) }

    val localeId = Locale.forLanguageTag("id-ID")
    val monthYearFormat = remember { SimpleDateFormat("MMMM yyyy", localeId) }
    val monthYearTitle = monthYearFormat.format(displayedCalendar.time).uppercase()

    // Ngikutin day
    val calCopy = displayedCalendar.clone() as Calendar
    calCopy.set(Calendar.DAY_OF_MONTH, 1)

    val firstDayOfWeek = calCopy.get(Calendar.DAY_OF_WEEK)
    val startOffset = (firstDayOfWeek + 5) % 7

    val daysInMonth = calCopy.getActualMaximum(Calendar.DAY_OF_MONTH)

    val greenStreakRes = getDrawableByName(context, "greenstreak")
    val streakRes = getDrawableByName(context, "streak")

    val primaryText = if (currentStreak > 0) {
        "Kamu memiliki $currentStreak hari streak aktif! Pertahankan!"
    } else {
        "Ayo mulai petualanganmu hari ini dan dapatkan streak pertamamu!"
    }

    val secondaryText = if (longestStreak > 0) {
        "Rekor tertinggi kamu adalah $longestStreak hari!"
    } else {
        "Bermain setiap hari untuk menjaga streak tetap aktif!"
    }

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
                .padding(Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header title card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Cek Streak Kamu",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        color = LeksinPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xl))

            // Banner streak card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = LeksinPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.xl),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (greenStreakRes != 0) {
                        Image(
                            painter = painterResource(id = greenStreakRes),
                            contentDescription = "Green Streak",
                            modifier = Modifier.size(54.dp)
                        )
                    } else {
                        Text(text = "🔥", fontSize = 40.sp)
                    }

                    Spacer(modifier = Modifier.width(Spacing.lg))

                    Column {
                        Text(
                            text = primaryText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        Text(
                            text = secondaryText,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xl))

            // Realtime kalnedear!!!
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, LeksinPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.lg),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header bulan
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "<",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            color = LeksinPrimary,
                            modifier = Modifier
                                .clickable {
                                    val prev = displayedCalendar.clone() as Calendar
                                    prev.add(Calendar.MONTH, -1)
                                    displayedCalendar = prev
                                }
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )

                        Text(
                            text = monthYearTitle,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = LeksinPrimary
                        )

                        Text(
                            text = ">",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            color = LeksinPrimary,
                            modifier = Modifier
                                .clickable {
                                    val next = displayedCalendar.clone() as Calendar
                                    next.add(Calendar.MONTH, 1)
                                    displayedCalendar = next
                                }
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(Spacing.lg))

                    // Row hari
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        listOf("SEN", "SEL", "RAB", "KAM", "JUM", "SAB", "MING").forEach { day ->
                            Text(
                                text = day,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = LeksinPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.sm))

                    // Grid kalndernjsdsdsccds
                    val totalCells = startOffset + daysInMonth
                    val totalRows = (totalCells + 6) / 7

                    val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    Column(
                        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
                    ) {
                        repeat(totalRows) { rowIndex ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                repeat(7) { colIndex ->
                                    val cellIndex = rowIndex * 7 + colIndex
                                    val dayNum = cellIndex - startOffset + 1

                                    if (dayNum in 1..daysInMonth) {
                                        val cellCal = displayedCalendar.clone() as Calendar
                                        cellCal.set(Calendar.DAY_OF_MONTH, dayNum)
                                        val cellDateStr = sdfDate.format(cellCal.time)

                                        val isStreakActive = streakDates.contains(cellDateStr)

                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (isStreakActive && streakRes == 0) LeksinPrimary
                                                    else Color(0xFFF0F0EE)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (isStreakActive && streakRes != 0) {
                                                Image(
                                                    painter = painterResource(id = streakRes),
                                                    contentDescription = "Streak Day $dayNum",
                                                    modifier = Modifier.size(28.dp)
                                                )
                                            } else {
                                                Text(
                                                    text = "$dayNum",
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontWeight = if (isStreakActive) FontWeight.Bold else FontWeight.Normal
                                                    ),
                                                    color = if (isStreakActive) Color.White else Color.DarkGray
                                                )
                                            }
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.size(36.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
