package com.example.onlypaws.repos

import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult
import com.google.firebase.auth.FirebaseUser

interface IUserAccountRepository {
    suspend fun getLoggedInUser(id: String): GetDbResult

    suspend fun tryRegisterUser(profile: UserProfile): SaveDbResult
}