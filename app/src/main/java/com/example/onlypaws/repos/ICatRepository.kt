package com.example.onlypaws.repos

import com.example.onlypaws.models.CatProfile

interface ICatRepository {
    suspend fun getCatProfiles() : List<CatProfile>
    suspend fun getCatProfile(catId : Int) : CatProfile

    suspend fun getLastCatProfile() : CatProfile
    suspend fun getSuggestedCatProfile() : CatProfile
}