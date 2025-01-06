package com.example.onlypaws.repos

import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult
import com.example.onlypaws.models.registerDetails.DetailState

interface IUserAccountRepository {
    suspend fun getLoggedInUser(email: String): GetDbResult

    suspend fun tryRegisterUser(details : DetailState): SaveDbResult

    suspend fun doesUserExist(email : String) : Boolean

    suspend fun changeViewedId(email : String, catId : Int) : SaveDbResult

    suspend fun addToLiked(email : String, catId : Int, liked : Boolean) : SaveDbResult
    suspend fun removeFromLiked(email : String, catId : Int, isLiked : Boolean) : SaveDbResult

    suspend fun getAllLiked(email: String, liked : Boolean) : GetDbResult
}