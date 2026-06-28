package com.example.givchurch.data.remote.api.service

import com.example.givchurch.data.remote.api.model.VerseResponse
import com.example.givchurch.data.remote.api.utils.BibleUrlGenerator
import com.example.givchurch.data.remote.api.utils.BibleTextCleaner
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BibleWebService(private val client: HttpClient) {

    suspend fun fetchRandomVerse(): VerseResponse {
        try {
            val dynamicPath = BibleUrlGenerator.generatePath()
            val urlPath = BibleUrlGenerator.buildUrl(dynamicPath)

            val response: VerseResponse = client.get(urlPath).body()
            val cleanText = BibleTextCleaner.clean(response.text)

            return response.copy(text = cleanText)
        } catch (e: Exception) {
            throw e
        }
    }
}
