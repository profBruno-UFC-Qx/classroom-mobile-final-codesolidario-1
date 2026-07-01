package com.example.givchurch.data.remote.api.model

import kotlinx.serialization.Serializable

@Serializable
data class VerseResponse(
    val reference: String,
    val text: String
)
