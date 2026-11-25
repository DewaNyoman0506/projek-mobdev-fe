package com.leksin.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.leksin.app.ui.theme.Spacing

// 6 Digit kotak
@Composable
fun LeksinOtpField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 6,
    isError: Boolean = false,
    supportingText: String? = null,
) {
    val cs = MaterialTheme.colorScheme
    Column(modifier) {
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it.filter(Char::isDigit).take(length)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            cursorBrush = SolidColor(cs.primary),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                    repeat(length) { i ->
                        val focused = i == value.length.coerceAtMost(length - 1)
                        val borderColor = when {
                            isError -> cs.error
                            focused -> cs.primary
                            else -> cs.outline
                        }
                        Surface(
                            modifier = Modifier.width(44.dp).height(48.dp),
                            shape = MaterialTheme.shapes.medium,
                            color = cs.surface,
                            border = BorderStroke(if (focused) 2.dp else 1.dp, borderColor),
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = value.getOrNull(i)?.toString().orEmpty(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = cs.onSurface,
                                )
                            }
                        }
                    }
                }
            },
        )
        if (supportingText != null) {
            Text(
                supportingText,
                modifier = Modifier.padding(top = Spacing.sm),
                style = MaterialTheme.typography.bodySmall,
                color = if (isError) cs.error else cs.onSurfaceVariant,
            )
        }
    }
}
