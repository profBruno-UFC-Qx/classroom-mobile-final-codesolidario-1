package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUserId(): String
    fun getUserIdFlow(): Flow<String>
    suspend fun updateProfile(user: User): Result<String>
    suspend fun getCurrentUserProfile(): Result<User>
}
