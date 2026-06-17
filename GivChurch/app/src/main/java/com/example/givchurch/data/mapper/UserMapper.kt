package com.example.givchurch.data.mapper

import com.example.givchurch.data.remote.firebase.model.User as LocalUser
import com.example.givchurch.domain.model.User as UserDomain


fun UserDomain.toLocalUser(): LocalUser {
    return LocalUser(
        id = this.id,
        firstname = this.firstname,
        lastname = this.lastname,
        email = this.email
    )
}

fun LocalUser.toRemoteUser(password: String = ""): UserDomain {
    return UserDomain(
        id = this.id,
        firstname = this.firstname,
        lastname = this.lastname,
        email = this.email,
        password = password
    )
}
