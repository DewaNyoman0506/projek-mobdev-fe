package com.leksin.app.ui.screens.child

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.leksin.app.ui.components.LeksinNavItem
import com.leksin.app.ui.components.LeksinNavigationBar

@Composable
fun ChildMainScreen(
    onStartGameSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val navItems = listOf(
        LeksinNavItem("Home", Icons.Filled.Home),
        LeksinNavItem("Collections", Icons.AutoMirrored.Filled.List),
        LeksinNavItem("Streaks", Icons.Filled.DateRange),
        LeksinNavItem("Settings", Icons.Filled.Settings)
    )

    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            when (selectedTab) {
                0 -> ChildHomeTab(onPlayClick = onStartGameSession)
                1 -> ChildCollectionsTab()
                2 -> ChildStreaksTab()
                3 -> ChildSettingsTab()
            }
        }
        LeksinNavigationBar(
            items = navItems,
            selectedIndex = selectedTab,
            onSelect = { selectedTab = it }
        )
    }
}
