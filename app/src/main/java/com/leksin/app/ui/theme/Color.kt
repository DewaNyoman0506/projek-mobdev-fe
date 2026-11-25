package inyo.pam.leksin.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


// Note, ini kalau mau ganti nama valnya juga boleh wkwkwkwk bebas
val LeksinPrimary = Color(0xFFFA6838)
val LeksinOnPrimary = Color(0xFF3A2F23)
val LeksinPrimaryContainer = Color(0xFFFCE1A3)
val LeksinOnPrimaryContainer = Color(0xFFB5361C)

val LeksinSecondary = Color(0xFFFCC850)
val LeksinOnSecondary = Color(0xFF3A2F23)
val LeksinSecondaryContainer = Color(0xFFFFE96E)
val LeksinOnSecondaryContainer = Color(0xFF3A2F23)

val LeksinTertiary = Color(0xFFD5E952)
val LeksinOnTertiary = Color(0xFF3A2F23)
val LeksinTertiaryContainer = Color(0xFFD5E952)
val LeksinOnTertiaryContainer = Color(0xFF2B5A1E)

val LeksinBackground = Color(0xFFFDFBEF)
val LeksinOnBackground = Color(0xFF3A2F23)
val LeksinSurface = Color(0xFFFFFFFF)
val LeksinOnSurface = Color(0xFF3A2F23)
val LeksinSurfaceVariant = Color(0xFFFCE1A3)
val LeksinOnSurfaceVariant = Color(0xFF5F5F6B)

val LeksinOutline = Color(0xFF5F5F6B)
val LeksinOutlineVariant = Color(0xFF9293AF)

val LeksinError = Color(0xFFB3261E)
val LeksinOnError = Color(0xFFFFFFFF)
val LeksinErrorContainer = Color(0xFFF9DEDC)
val LeksinOnErrorContainer = Color(0xFF410E0B)

@Immutable
data class LeksinExtraColors(
    val disabledContainer: Color = Color(0xFFDDDDDD),
    val onDisabled: Color = Color(0xFF8A8A8A),
    val primaryPressed: Color = Color(0xFFE96A36),
    val secondaryPressed: Color = Color(0xFFECB84C),
    val outlinedPressed: Color = Color(0xFFEFEADF),
)

val LocalLeksinExtraColors = staticCompositionLocalOf { LeksinExtraColors() }
