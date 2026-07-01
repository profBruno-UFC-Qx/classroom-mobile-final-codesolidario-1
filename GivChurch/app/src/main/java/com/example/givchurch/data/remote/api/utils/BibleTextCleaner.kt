package com.example.givchurch.data.remote.api.utils

object BibleTextCleaner {

    fun clean(text: String): String {
        return text
            .replace("\\p{Pd}".toRegex(), "")
            .replace("\u00AD", "")
            .trim()
    }
}
