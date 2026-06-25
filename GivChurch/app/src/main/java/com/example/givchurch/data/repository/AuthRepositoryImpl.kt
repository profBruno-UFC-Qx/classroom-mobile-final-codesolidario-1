package com.example.givchurch.data.repository

import com.example.givchurch.data.mapper.toLocalUser
import com.example.givchurch.data.remote.firebase.service.FirebaseAuthService
import com.example.givchurch.domain.model.User
import com.example.givchurch.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: FirebaseAuthService
) : AuthRepository {

    override suspend fun register(user: User): Result<String> {
        val firebaseUser = user.toLocalUser().copy(password = user.password)
        return authService.register(firebaseUser)
    }

    override suspend fun login(user: User): Result<String> {
        val firebaseUser = user.toLocalUser().copy(password = user.password)
        return authService.login(firebaseUser)
    }

    override suspend fun resetPassword(email: String): Result<String> {
        return authService.resetPassword(email)
    }

    override fun logout() {
        authService.logout()
    }
}
