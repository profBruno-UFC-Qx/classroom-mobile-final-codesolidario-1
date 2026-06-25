package com.example.givchurch.data.remote.firebase.service

import com.example.givchurch.data.remote.firebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAuthService {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun register(user: User): Result<String> {
        return if (user.email.isNotBlank() && user.password.isNotBlank()) {
            try {
                auth.createUserWithEmailAndPassword(user.email, user.password).await()
                val userId = auth.currentUser?.uid ?: ""

                val userToSave = mapOf(
                    "id" to userId,
                    "firstname" to user.firstname,
                    "lastname" to user.lastname,
                    "email" to user.email,
                    "imageUrl" to user.imageUrl
                )

                firestore.collection("users").document(userId).set(userToSave).await()
                Result.success("Cadastro e dados salvos com sucesso!")
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("Preencha todos os campos"))
        }
    }

    suspend fun login(user: User): Result<String> {
        return if (user.email.isNotBlank() && user.password.isNotBlank()) {
            try {
                auth.signInWithEmailAndPassword(user.email, user.password).await()
                Result.success("Login realizado com sucesso")
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("Preencha todos os campos"))
        }
    }

    suspend fun resetPassword(email: String): Result<String> {
        return if (email.isNotBlank()) {
            try {
                auth.sendPasswordResetEmail(email).await()
                Result.success("E-mail de recuperação enviado com sucesso!")
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("Por favor, digite o seu e-mail."))
        }
    }

    fun logout() {
        auth.signOut()
    }
}
