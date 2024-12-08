package com.example.onlypaws.repos

import com.example.onlypaws.models.db.GetDbResult

interface ICatRepository {
    suspend fun getCatProfile(catId : Int) : GetDbResult
    suspend fun getLastCatProfile() : GetDbResult
    suspend fun getSuggestedCatProfile() : GetDbResult
}