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
                "email" to user.email,
                "imageUrl" to user.imageUrl
            )
            usersCollection.document(userId).update(userMap).await()
            Result.success("Profile updated successfully!")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getUserProfileFlow(userId: String): Flow<User?> {
        return callbackFlow {
            val listener = usersCollection.document(userId).addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    trySend(snapshot.toObject(User::class.java))
                } else {
                    trySend(User())
                }
            }
            awaitClose { listener.remove() }
        }
    }
}
