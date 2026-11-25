package com.leksin.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.leksin.app.ui.theme.Sizes
import com.leksin.app.ui.theme.leksin

// Button n sejenis

enum class LeksinButtonStyle { Filled, Outlined, Tonal }

@Composable
fun LeksinButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: LeksinButtonStyle = LeksinButtonStyle.Filled,
    enabled: Boolean = true,
) {
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val cs = MaterialTheme.colorScheme
    val x = MaterialTheme.leksin

    val container = when {
        !enabled -> x.disabledContainer
        style == LeksinButtonStyle.Filled -> if (pressed) x.primaryPressed else cs.primary
        style == LeksinButtonStyle.Tonal -> if (pressed) x.secondaryPressed else cs.secondary
        else -> if (pressed) x.outlinedPressed else Color.Transparent
    }
    val content = when {
        !enabled -> x.onDisabled
        style == LeksinButtonStyle.Outlined -> cs.onPrimaryContainer
        else -> cs.onPrimary
    }
    val border = if (style == LeksinButtonStyle.Outlined && enabled)
        BorderStroke(1.5.dp, cs.primary) else null

    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = Sizes.buttonHeight),
        enabled = enabled,
        shape = MaterialTheme.shapes.large,
        border = border,
        interactionSource = source,
        colors = ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = content,
            disabledContainerColor = container,
            disabledContentColor = content,
        ),
        elevation = null,
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}
