package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.User

interface AuthRepository {
    suspend fun register(user: User): Result<String>
    suspend fun login(user: User): Result<String>
    fun logout()
}

