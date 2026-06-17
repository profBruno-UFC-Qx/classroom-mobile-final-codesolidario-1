package com.example.givchurch.viewmodel.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.givchurch.data.remote.firebase.model.User
import com.example.givchurch.data.repository.AuthRepositoryImpl

class RegisterViewModel : ViewModel() {

    private val repository = AuthRepositoryImpl()

    var firstname = mutableStateOf("")
        private set

    var lastname = mutableStateOf("")
        private set

    var email = _root_ide_package_.androidx.compose.runtime.mutableStateOf("")
        private set

    var password = _root_ide_package_.androidx.compose.runtime.mutableStateOf("")
        private set

    var message = _root_ide_package_.androidx.compose.runtime.mutableStateOf("")
        private set

    fun onFirstnameChange(value: String) {
        firstname.value = value
    }

    fun onLastnameChange(value: String) {
        lastname.value = value
    }

    fun onEmailChange(value: String) {
        email.value = value
    }

    fun onPasswordChange(value: String) {
        password.value = value
    }

    fun register() {

        val success = repository.register(
            User(
                0,
                firstname.value,
                lastname.value,
                email.value,
                password.value
            )
        )

        message.value =
            if (success)
                "Usuário registrado!"
            else
                "E-mail já cadastrado."
    }
}