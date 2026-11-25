package inyo.pam.leksin.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import inyo.pam.leksin.ui.theme.Sizes

data class LeksinNavItem(val label: String, val icon: ImageVector)

// NavBar mockup
@Composable
fun LeksinNavigationBar(
    items: List<LeksinNavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cs = MaterialTheme.colorScheme
    NavigationBar(
        modifier = modifier.height(Sizes.navBarHeight),
        containerColor = cs.surface,
    ) {
        items.forEachIndexed { i, item ->
            NavigationBarItem(
                selected = i == selectedIndex,
                onClick = { onSelect(i) },
                icon = { Icon(item.icon, contentDescription = null, modifier = Modifier.size(Sizes.navIcon)) },
                label = { Text(item.label, style = MaterialTheme.typography.labelMedium) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = cs.secondaryContainer,
                    selectedIconColor = cs.onSecondaryContainer,
                    selectedTextColor = cs.onSurface,
                    unselectedIconColor = cs.onSurfaceVariant,
                    unselectedTextColor = cs.onSurfaceVariant,
                ),
            )
        }
    }
}
