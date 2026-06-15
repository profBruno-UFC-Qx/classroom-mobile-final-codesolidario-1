package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.User

interface AuthRepository {
    suspend fun register(user: User, password: String): Boolean
    suspend fun login(email: String, password: String): User?
}