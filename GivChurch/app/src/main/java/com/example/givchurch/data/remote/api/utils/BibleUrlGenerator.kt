package com.example.givchurch.data.remote.api.utils

import kotlin.random.Random

object BibleUrlGenerator {

    fun generatePath(): String {
        val randomBook = BibleStructure.inspiringBooks.random()
        val randomChapter = Random.nextInt(1, randomBook.totalChapters + 1)
        val randomVerse = Random.nextInt(1, 6)
        return "${randomBook.name}+$randomChapter:$randomVerse"
    }

    fun buildUrl(dynamicPath: String): String {
        return "https://bible-api.com/$dynamicPath?translation=almeida"
    }
}
