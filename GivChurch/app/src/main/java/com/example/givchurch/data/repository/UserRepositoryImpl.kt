package com.example.givchurch.data.repository

import com.example.givchurch.data.mapper.toLocalUser
import com.example.givchurch.data.mapper.toRemoteUser
import com.example.givchurch.data.remote.firebase.service.FirebaseUserService
import com.example.givchurch.domain.model.User
import com.example.givchurch.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userService: FirebaseUserService
) : UserRepository {

    override fun getCurrentUserId(): String {
        return userService.getCurrentUserId()
    }

    override suspend fun updateProfile(user: User): Result<String> {
        val firebaseUser = user.toLocalUser()
        return userService.updateProfile(firebaseUser)
    }

    override suspend fun getCurrentUserProfile(): Result<User> {
        return userService.getCurrentUserProfile().fold(
            onSuccess = { firebaseUser ->
                Result.success(firebaseUser.toRemoteUser())
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}
