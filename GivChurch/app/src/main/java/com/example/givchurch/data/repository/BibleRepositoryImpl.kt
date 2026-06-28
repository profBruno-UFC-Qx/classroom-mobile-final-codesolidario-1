package com.example.givchurch.data.repository

import com.example.givchurch.data.mapper.toDomain
import com.example.givchurch.data.remote.api.service.BibleWebService
import com.example.givchurch.domain.model.Verse
import com.example.givchurch.domain.repository.BibleRepository

class BibleRepositoryImpl(
    private val webService: BibleWebService
) : BibleRepository {

    override suspend fun getVerseOfTheDay(): Result<Verse> {
        return try {
            val response = webService.fetchRandomVerse()
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
