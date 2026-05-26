package com.example.givchurch.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.givchurch.data.repository.AuthRepository

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var message = mutableStateOf("")
        private set

    fun onEmailChange(value: String) {
        email.value = value
    }

    fun onPasswordChange(value: String) {
        password.value = value
    }

    fun login() {

        val user = repository.login(
            email.value,
            password.value
        )

        message.value =
            if (user != null)
                "Bem-vindo ${user.firstname}"
            else
                "Credenciais inválidas."
    }
}