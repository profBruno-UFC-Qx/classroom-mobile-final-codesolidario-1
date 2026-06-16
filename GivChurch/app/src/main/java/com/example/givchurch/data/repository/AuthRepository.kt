package com.example.givchurch.data.repository

import com.example.givchurch.data.mock.UserMockData
import com.example.givchurch.data.remote.firebase.model.User

class AuthRepository {
    fun register(user: User): Boolean {

        val userExists = UserMockData.users.any {
            it.email == user.email
        }

        if (userExists) {
            return false
        }

        val nextId = (UserMockData.users.maxOfOrNull { it.id } ?: 0) + 1
        val newUserWithId = user.copy(id = nextId)

        UserMockData.users.add(newUserWithId)
        return true
    }

    fun login(
        email: String,
        password: String
    ): User? {

        return UserMockData.users.find {
            it.email == email &&
                    it.password == password
        }
    }
}