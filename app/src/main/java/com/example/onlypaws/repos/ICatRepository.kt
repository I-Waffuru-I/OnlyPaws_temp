package com.example.onlypaws.repos

import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult

interface ICatRepository {
    suspend fun getCatProfileFromID(catId : Int) : GetDbResult
    suspend fun getCatProfileFromEmail(catId : String) : GetDbResult
    suspend fun getLastCatProfile() : GetDbResult
    suspend fun getSuggestedCatProfile() : GetDbResult
    suspend fun updateCatProfileInformation(catId : Int, property : String, value : String) : SaveDbResult
}