package com.example.onlypaws.repos

import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.db.GetDbResult
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class FirebaseCatRepo : ICatRepository {

    private val database = Firebase.database

    override suspend fun getCatProfile(catId: Int): GetDbResult {

        return try {
            val id = catId.toString()

            // WHYYYYYYYYYY
            val sn = database.getReference("cats").child(id).get().await()
            val cat : CatProfile? = sn.getValue(CatProfile::class.java)
            if(cat != null) GetDbResult.Success(cat)
            else GetDbResult.Failure("Cat info couldn't be parsed correctly.")
        } catch (e : Exception) {
            GetDbResult.Failure(e.message.toString())
        }
    }

    override suspend fun getLastCatProfile(): GetDbResult {
        TODO("Not yet implemented")
    }

    override suspend fun getSuggestedCatProfile(): GetDbResult {
        TODO("Not yet implemented")
    }
}