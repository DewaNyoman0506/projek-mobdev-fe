package com.leksin.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.leksin.app.ui.theme.Spacing
import com.leksin.app.ui.theme.leksin

// Textfield

@Composable
fun LeksinTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
) {
    val cs = MaterialTheme.colorScheme
    Column(modifier) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = cs.onSurface)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().padding(top = Spacing.xs),
            enabled = enabled,
            isError = isError,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = placeholder?.let { { Text(it, style = MaterialTheme.typography.bodyMedium) } },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = cs.primary,
                unfocusedBorderColor = cs.outline,
                errorBorderColor = cs.error,
                disabledBorderColor = MaterialTheme.leksin.disabledContainer,
                focusedContainerColor = cs.surface,
                unfocusedContainerColor = cs.surface,
                errorContainerColor = cs.surface,
                disabledContainerColor = MaterialTheme.leksin.disabledContainer,
                disabledTextColor = MaterialTheme.leksin.onDisabled,
                disabledPlaceholderColor = MaterialTheme.leksin.onDisabled,
                cursorColor = cs.primary,
                focusedTextColor = cs.onSurface,
                unfocusedTextColor = cs.onSurface,
                unfocusedPlaceholderColor = cs.onSurfaceVariant,
                focusedPlaceholderColor = cs.onSurfaceVariant,
            ),
        )
        if (supportingText != null) {
            Text(
                supportingText,
                modifier = Modifier.padding(top = Spacing.xs),
                style = MaterialTheme.typography.bodySmall,
                color = if (isError) cs.error else cs.onSurfaceVariant,
            )
        }
    }
}
