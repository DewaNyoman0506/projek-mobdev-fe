package inyo.pam.leksin.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

// Extra

private val LeksinLightColorScheme = lightColorScheme(
    primary = LeksinPrimary,
    onPrimary = LeksinOnPrimary,
    primaryContainer = LeksinPrimaryContainer,
    onPrimaryContainer = LeksinOnPrimaryContainer,
    secondary = LeksinSecondary,
    onSecondary = LeksinOnSecondary,
    secondaryContainer = LeksinSecondaryContainer,
    onSecondaryContainer = LeksinOnSecondaryContainer,
    tertiary = LeksinTertiary,
    onTertiary = LeksinOnTertiary,
    tertiaryContainer = LeksinTertiaryContainer,
    onTertiaryContainer = LeksinOnTertiaryContainer,
    background = LeksinBackground,
    onBackground = LeksinOnBackground,
    surface = LeksinSurface,
    onSurface = LeksinOnSurface,
    surfaceVariant = LeksinSurfaceVariant,
    onSurfaceVariant = LeksinOnSurfaceVariant,
    surfaceContainer = LeksinSurface,
    surfaceContainerLowest = LeksinSurface,
    surfaceContainerLow = LeksinSurface,
    surfaceContainerHigh = LeksinSurface,
    surfaceContainerHighest = LeksinSurface,
    outline = LeksinOutline,
    outlineVariant = LeksinOutlineVariant,
    error = LeksinError,
    onError = LeksinOnError,
    errorContainer = LeksinErrorContainer,
    onErrorContainer = LeksinOnErrorContainer,
)

@Composable
fun LeksinTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLeksinExtraColors provides LeksinExtraColors()) {
        MaterialTheme(
            colorScheme = LeksinLightColorScheme,
            typography = LeksinTypography,
            shapes = LeksinShapes,
            content = content,
        )
    }
}

val MaterialTheme.leksin: LeksinExtraColors
    @Composable @ReadOnlyComposable
    get() = LocalLeksinExtraColors.current
