package com.example.givchurch.data.remote.firebase.service

import com.example.givchurch.data.remote.firebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseUserService {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun updateProfile(user: User): Result<String> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Usuário não está logado."))

        val userMap = mapOf(
            "id" to userId,
            "firstname" to user.firstname,
            "lastname" to user.lastname,
            "email" to user.email
        )

        return try {
            usersCollection.document(userId).update(userMap).await()
            Result.success("Perfil atualizado com sucesso!")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUserProfile(): Result<User> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Usuário não está logado."))

        return try {
            val document = usersCollection.document(userId).get().await()
            if (document != null && document.exists()) {
                val user = document.toObject(User::class.java)
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Erro ao converter dados do usuário."))
                }
            } else {
                Result.failure(Exception("Perfil não encontrado."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
