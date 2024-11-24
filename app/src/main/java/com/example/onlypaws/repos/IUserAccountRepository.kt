package com.example.onlypaws.repos

import com.google.firebase.auth.FirebaseUser

interface IUserAccountRepository {
    suspend fun getLoggedInUser() : FirebaseUser?

    suspend fun logInUser(username : String, password : String) : FirebaseUser?

    suspend fun logOutUser()

    suspend fun tryRegisterUser(username : String, password : String)
}