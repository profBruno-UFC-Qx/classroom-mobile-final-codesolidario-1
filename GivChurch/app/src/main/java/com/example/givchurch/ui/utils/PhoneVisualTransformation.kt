package com.example.givchurch.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 11) text.text.substring(0, 11) else text.text
        var out = ""

        for (i in trimmed.indices) {
            if (i == 0) out += "("
            out += trimmed[i]
            if (i == 1) out += ") "
            if (i == 2) out += " "
            if (i == 6) out += "-"
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val transformedOffset = when {
                    offset <= 1 -> offset + 1
                    offset <= 2 -> offset + 3
                    offset <= 6 -> offset + 4
                    offset <= 11 -> offset + 5
                    else -> 16
                }
                return transformedOffset.coerceIn(0, out.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val originalOffset = when {
                    offset <= 2 -> (offset - 1).coerceAtLeast(0)
                    offset <= 4 -> (offset - 3).coerceAtLeast(0)
                    offset <= 10 -> (offset - 4).coerceAtLeast(0)
                    offset <= 16 -> (offset - 5).coerceAtLeast(0)
                    else -> 11
                }
                return originalOffset.coerceIn(0, text.text.length)
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}
