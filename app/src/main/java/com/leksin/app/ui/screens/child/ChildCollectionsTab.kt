package com.leksin.app.ui.screens.child

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leksin.app.R
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

data class CatCollectionItem(
    val stageId: String,
    val name: String,
    val unmaskedImageRes: Int,
    val funFact: String
)

object CatCollectionPrefs {
    private const val PREFS_NAME = "leksin_cat_collections"

    fun isUnlocked(context: Context, stageId: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean("unlocked_$stageId", false)
    }

    fun unlockStage(context: Context, stageId: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean("unlocked_$stageId", true).apply()
    }
}

@Composable
fun ChildCollectionsTab(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val cats = listOf(
        CatCollectionItem("A", "Tabby Cat", R.drawable.tabby, "Kucing Tabby memiliki tanda garis-garis unik di dahi mereka menyerupai huruf 'M'."),
        CatCollectionItem("B", "Siamese Cat", R.drawable.siamese, "Kucing Siamese terkenal sangat aktif, pandai mengeong, dan memiliki mata biru yang indah."),
        CatCollectionItem("C", "Black Cat", R.drawable.blackcat, "Di banyak kebudayaan, kucing hitam dipercaya sebagai simbol keberuntungan dan pembawa hoki!"),
        CatCollectionItem("D", "Calico Cat", R.drawable.calico, "Hampir semua kucing Calico berjenis kelamin betina karena gen warna bulunya!")
    )

    var lockedCatToInspect by remember { mutableStateOf<CatCollectionItem?>(null) }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.awan1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth
        )

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
                        text = "Collections",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        color = LeksinPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.xl))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Spacing.md)
            ) {
                items(cats) { cat ->
                    val unlocked = CatCollectionPrefs.isUnlocked(context, cat.stageId)

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !unlocked) {
                                if (!unlocked) {
                                    lockedCatToInspect = cat
                                }
                            },
                        shape = RoundedCornerShape(24.dp),
                        color = if (unlocked) LeksinPrimary else Color(0xFFE5E5E5),
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Spacing.lg),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Cat Avatar
                            Surface(
                                modifier = Modifier.size(64.dp),
                                shape = CircleShape,
                                color = Color.White,
                                shadowElevation = 2.dp
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Image(
                                        painter = painterResource(
                                            id = if (unlocked) cat.unmaskedImageRes else R.drawable.unknowncat
                                        ),
                                        contentDescription = cat.name,
                                        modifier = Modifier.size(52.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(Spacing.lg))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = if (unlocked) cat.name else "???",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = if (unlocked) Color.White else Color.DarkGray
                                )
                                Spacer(modifier = Modifier.height(Spacing.xs))
                                Text(
                                    text = if (unlocked) cat.funFact else "Selesaikan Stage ${cat.stageId} dengan 3 hati penuh untuk mengadopsi kucing ini!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (unlocked) Color.White.copy(alpha = 0.9f) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Cat lock
    if (lockedCatToInspect != null) {
        val cat = lockedCatToInspect!!
        AlertDialog(
            onDismissRequest = { lockedCatToInspect = null },
            title = { Text("Kucing Masih Terkunci 🔒") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 4.dp
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = cat.unmaskedImageRes),
                                contentDescription = cat.name,
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(Spacing.md))
                    Text(
                        text = "Kucing ini (${cat.name}) masih terkunci! Selesaikan Stage ${cat.stageId} dengan 3 hati penuh untuk membukanya.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { lockedCatToInspect = null }) {
                    Text("Mengerti")
                }
            }
        )
    }
}
