package com.example.givchurch.data.mock

import com.example.givchurch.data.remote.api.model.VerseResponse
import com.example.givchurch.domain.model.Verse

object VerseMockData {

    val response = VerseResponse(
        reference = "Salmos 23:1",
        text = "O Senhor é o meu pastor, nada me faltará."
    )

    val domain = Verse(
        reference = "Salmos 23:1",
        text = "O Senhor é o meu pastor, nada me faltará."
    )
}
