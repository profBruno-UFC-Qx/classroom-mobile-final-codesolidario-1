package com.example.givchurch.data.mapper

import com.example.givchurch.data.remote.api.model.VerseResponse
import com.example.givchurch.domain.model.Verse

fun VerseResponse.toDomain(): Verse {
    return Verse(
        reference = this.reference,
        text = this.text
    )
}
