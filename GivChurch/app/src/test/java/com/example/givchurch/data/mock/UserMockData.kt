package com.example.givchurch.data.mock

import com.example.givchurch.data.remote.firebase.model.User as LocalUser
import com.example.givchurch.domain.model.User as UserDomain

object UserMockData {

    val localUserList = listOf(
        LocalUser(
            id = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH",
            firstname = "Rubens",
            lastname = "Rabelo",
            email = "rubens@email.com",
            imageUrl = "content://media/external/images/media/10"
        ),
        LocalUser(
            id = "bV8cR3tY6mK5jHW4k9Xz7Pq2LmN1",
            firstname = "Maria",
            lastname = "Souza",
            email = "maria@email.com",
            imageUrl = ""
        )
    )

    val domainUserList = listOf(
        UserDomain(
            id = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH",
            firstname = "Rubens",
            lastname = "Rabelo",
            email = "rubens@email.com",
            password = "",
            imageUrl = "content://media/external/images/media/10"
        ),
        UserDomain(
            id = "bV8cR3tY6mK5jHW4k9Xz7Pq2LmN1",
            firstname = "Maria",
            lastname = "Souza",
            email = "maria@email.com",
            password = "securePassword123",
            imageUrl = ""
        )
    )
}
