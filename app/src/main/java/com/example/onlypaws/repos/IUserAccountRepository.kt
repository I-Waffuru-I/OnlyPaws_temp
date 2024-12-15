package com.example.onlypaws.repos

import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult
import com.example.onlypaws.models.registerDetails.DetailState

interface IUserAccountRepository {
    suspend fun getLoggedInUser(id: String): GetDbResult

    suspend fun tryRegisterUser(details : DetailState): SaveDbResult

    suspend fun doesUserExist(email : String) : Boolean
}