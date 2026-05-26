package com.example.givchurch.data.mock

import com.example.givchurch.data.model.User

object UserMockData {

    val users = mutableListOf(
        User(
            firstname = "Rubens",
            lastname = "Rabelo",
            email = "rubens@gmail.com",
            password = "123456"
        ),

        User(
            firstname = "Maria",
            lastname = "Silva",
            email = "maria@gmail.com",
            password = "abcdef"
        )
    )
}