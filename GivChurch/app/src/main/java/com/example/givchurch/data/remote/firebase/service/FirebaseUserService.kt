package com.example.givchurch.data.remote.firebase.service

import com.example.givchurch.data.remote.firebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseUserService {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: throw IllegalStateException("User is not logged in.")
    }

    fun getUserIdFlow(): Flow<String> {
        return callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                try {
                    trySend(getCurrentUserId())
                } catch (e: Exception) {
                    trySend("")
                }
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }
    }

    suspend fun updateProfile(user: User): Result<String> {
        return try {
            val userId = getCurrentUserId()
            val userMap = mapOf(
                "id" to userId,
                "firstname" to user.firstname,
                "lastname" to user.lastname,
                "email" to user.email
            )
            usersCollection.document(userId).update(userMap).await()
            Result.success("Profile updated successfully!")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUserProfile(): Result<User> {
        return try {
            val userId = getCurrentUserId()
            val document = usersCollection.document(userId).get().await()
            if (document != null && document.exists()) {
                val user = document.toObject(User::class.java)
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Error converting user data."))
                }
            } else {
                Result.failure(Exception("Profile not found."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
