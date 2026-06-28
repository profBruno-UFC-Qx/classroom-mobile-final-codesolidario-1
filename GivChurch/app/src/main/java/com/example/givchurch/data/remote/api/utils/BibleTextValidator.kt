package com.example.givchurch.data.remote.api.utils

import com.example.givchurch.data.remote.api.model.VerseResponse

object BibleTextValidator {

    fun validateLength(response: VerseResponse, cleanText: String): VerseResponse? {
        if (cleanText.length <= 85) {
            return response.copy(text = cleanText)
        }
        return null
    }
}
