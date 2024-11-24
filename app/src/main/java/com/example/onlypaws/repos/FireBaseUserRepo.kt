package com.example.onlypaws.repos

import com.google.firebase.auth.FirebaseUser

class FireBaseUserRepo : IUserAccountRepository {
    override suspend fun getLoggedInUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override suspend fun logInUser(username: String, password: String): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override suspend fun logOutUser() {
        TODO("Not yet implemented")
    }

    override suspend fun tryRegisterUser(username: String, password: String) {
        TODO("Not yet implemented")
    }
}