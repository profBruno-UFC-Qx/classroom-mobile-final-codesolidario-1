package com.example.givchurch.data.mock

import com.example.givchurch.data.model.User

object UserMockData {

    val users = mutableListOf(
        User(
            id = 1,
            firstname = "Rubens",
            lastname = "Rabelo",
            email = "rubens@gmail.com",
            password = "123456"
        ),

        User(
            id = 2,
            firstname = "Maria",
            lastname = "Silva",
            email = "maria@gmail.com",
            password = "abcdef"
        )
    )
}