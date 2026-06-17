package com.example.givchurch.data.mock

import com.example.givchurch.data.remote.firebase.model.User

object UserMockData {

    val users = mutableListOf(
        User(
            id = "5a8f6b2d-9c14-4e3a-b82f-7e9c3b4a5d12",
            firstname = "Rubens",
            lastname = "Rabelo",
            email = "rubens@gmail.com",
            password = "123456"
        ),

        User(
            id = "d1e8f2c3-a4b5-4c6d-9e8f-7a6b5c4d3e2f",
            firstname = "Maria",
            lastname = "Silva",
            email = "maria@gmail.com",
            password = "abcdef"
        )
    )
}