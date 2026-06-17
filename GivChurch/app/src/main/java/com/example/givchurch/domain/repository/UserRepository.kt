package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.User

interface UserRepository {
    fun getCurrentUserId(): String?
    suspend fun updateProfile(user: User): Result<String>
    suspend fun getCurrentUserProfile(): Result<User>
}

