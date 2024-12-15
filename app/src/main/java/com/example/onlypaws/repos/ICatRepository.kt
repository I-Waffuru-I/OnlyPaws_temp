package com.example.onlypaws.repos

import com.example.onlypaws.models.db.GetDbResult

interface ICatRepository {
    suspend fun getCatProfileFromEmail(catId : Int) : GetDbResult
    suspend fun getCatProfileFromEmail(catId : String) : GetDbResult
    suspend fun getLastCatProfile() : GetDbResult
    suspend fun getSuggestedCatProfile() : GetDbResult
}