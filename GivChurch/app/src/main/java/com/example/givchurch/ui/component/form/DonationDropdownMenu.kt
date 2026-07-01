package com.example.givchurch.ui.component.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DonationDropdownMenu(
    label: String,
    options: List<T>,
    selectedOption: T?,
    optionToString: (T) -> String,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Selecione uma opção"
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedOption?.let { optionToString(it) } ?: placeholderText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(optionToString(option)) },
                    onClick = {
                        onOptionSelect(option)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}
