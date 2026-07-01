package com.example.givchurch.data.repository

import com.example.givchurch.data.mapper.toLocalUser
import com.example.givchurch.data.mapper.toRemoteUser
import com.example.givchurch.data.remote.firebase.service.FirebaseUserService
import com.example.givchurch.domain.model.User
import com.example.givchurch.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userService: FirebaseUserService
) : UserRepository {

    override fun getCurrentUserId(): String {
        return userService.getCurrentUserId()
    }

    override fun getUserIdFlow(): Flow<String> {
        return userService.getUserIdFlow()
    }

    override suspend fun updateProfile(user: User): Result<String> {
        val firebaseUser = user.toLocalUser()
        return userService.updateProfile(firebaseUser)
    }

    override fun getUserProfileFlow(userId: String): Flow<User?> {
        return userService.getUserProfileFlow(userId).map { firebaseUser ->
            firebaseUser?.toRemoteUser()
                ?: User(id = userId, firstname = "Voluntário", lastname = "", email = "")
        }
    }
}
