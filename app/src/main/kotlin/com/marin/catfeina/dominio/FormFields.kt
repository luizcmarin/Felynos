// =============================================================================
// Arquivo: com.marin.catfeina.ui.dominio.FormFields.kt
// Descrição: Componentes de UI reutilizáveis para formulários.
// =============================================================================
package com.marin.catfeina.dominio

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization

@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelResId: Int,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    @StringRes supportingTextResId: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
    singleLine: Boolean = true,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(labelResId)) },
        isError = isError,
        supportingText = supportingTextResId?.let { { Text(stringResource(it)) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppExposedDropdownMenu(
    labelResId: Int,
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    selectedOptionToString: @Composable (T) -> String,
    dropdownItemContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    leadingIconProvider: ((T) -> ImageVector?)? = null,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val currentSelectedOptionText = selectedOptionToString(selectedOption)

    ExposedDropdownMenuBox(
        expanded = expanded && enabled,
        onExpandedChange = { if (enabled) expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable),
            readOnly = true,
            value = currentSelectedOptionText,
            onValueChange = {},
            label = { Text(stringResource(labelResId)) },
            leadingIcon = leadingIconProvider?.let { provider ->
                provider(selectedOption)?.let { icon ->
                    { Icon(imageVector = icon, contentDescription = null) }
                }
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded && enabled) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            enabled = enabled
        )

        ExposedDropdownMenu(
            expanded = expanded && enabled,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { dropdownItemContent(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
