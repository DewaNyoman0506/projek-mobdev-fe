package com.leksin.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.LeksinSecondary
import com.leksin.app.ui.theme.Spacing

/**
 * Custom Canvas Back Arrow (<-) icon.
 */
@Composable
fun BackArrowIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2.5.dp.toPx()

        drawLine(
            color = tint,
            start = Offset(w * 0.15f, h * 0.5f),
            end = Offset(w * 0.85f, h * 0.5f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.15f, h * 0.5f),
            end = Offset(w * 0.45f, h * 0.2f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.15f, h * 0.5f),
            end = Offset(w * 0.45f, h * 0.8f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}

/**
 * Custom Canvas Forward Arrow (->) icon.
 */
@Composable
fun ForwardArrowIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2.5.dp.toPx()

        drawLine(
            color = tint,
            start = Offset(w * 0.15f, h * 0.5f),
            end = Offset(w * 0.85f, h * 0.5f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.85f, h * 0.5f),
            end = Offset(w * 0.55f, h * 0.2f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.85f, h * 0.5f),
            end = Offset(w * 0.55f, h * 0.8f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}


/**
 * Common background container matching the reference design:
 * Top area: Bright yellow (#FCC850 / LeksinSecondary)
 * Bottom area: Rounded white/cream bottom sheet card
 */
@Composable
fun LeksinAuthContainer(
    modifier: Modifier = Modifier,
    headerContent: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LeksinSecondary)
    ) {
        // Top yellow space for illustration / header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            contentAlignment = Alignment.Center
        ) {
            headerContent()
        }

        // Bottom sheet card with rounded top corners pinned to bottom
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(horizontal = Spacing.xxl, vertical = Spacing.xxxl)
            ) {
                content()
            }
        }
    }
}

/**
 * Header bar with circular back button (<-) and title text in LeksinPrimary orange.
 */
@Composable
fun LeksinHeaderTitle(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackClick != null) {
            Box(
                modifier = Modifier
                    .size(36.dp)
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
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(Spacing.md))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            ),
            color = LeksinPrimary
        )
    }
}

/**
 * Pill-shaped primary button (Masuk ->, Daftar ->, Submit ->) matching design.
 */
@Composable
fun LeksinPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showArrow: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.heightIn(min = 48.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = LeksinPrimary,
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFE2E2E2),
            disabledContentColor = Color(0xFF9E9E9E)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = Spacing.xs)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = if (enabled) Color.White else Color(0xFF9E9E9E)
            )

            if (showArrow) {
                Spacer(modifier = Modifier.width(Spacing.sm))
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(if (enabled) Color.White else Color(0xFFB0B0B0)),
                    contentAlignment = Alignment.Center
                ) {
                    ForwardArrowIcon(
                        tint = if (enabled) LeksinPrimary else Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

/**
 * Outlined Pill text field with rounded orange border.
 */
@Composable
fun LeksinPillTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isPassword: Boolean = false,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardType: KeyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
) {
    val orangeBorder = LeksinPrimary

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium.copy(color = LeksinPrimary.copy(alpha = 0.7f))
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = isError,
            shape = RoundedCornerShape(28.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = orangeBorder,
                unfocusedBorderColor = orangeBorder,
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = orangeBorder
            )
        )
        if (supportingText != null) {
            Text(
                text = supportingText,
                modifier = Modifier.padding(start = Spacing.md, top = Spacing.xs),
                style = MaterialTheme.typography.bodySmall,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Selectable role button option for "Orang Tua" / "Anak" selection screen.
 */
@Composable
fun LeksinRoleOptionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(18.dp),
        color = if (isSelected) LeksinPrimary else Color(0xFFE5E5E5),
        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
