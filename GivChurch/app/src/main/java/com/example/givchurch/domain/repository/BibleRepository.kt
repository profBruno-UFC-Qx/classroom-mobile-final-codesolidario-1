package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.Verse

interface BibleRepository {
    suspend fun getVerseOfTheDay(): Result<Verse>
}
