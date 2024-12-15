package com.example.onlypaws.repos

import android.util.Base64
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await

class FirebaseCatRepo : ICatRepository {

    private val database = Firebase.database

    override suspend fun getCatProfileFromEmail(catId: Int): GetDbResult {

        return try {
            val id = catId.toString()
            val sn = database.getReference("cats").child(id).get().await()
            val cat : CatProfile? = sn.getValue(CatProfile::class.java)
            if(cat != null) GetDbResult.Success(cat)
            else GetDbResult.Failure("Cat info couldn't be parsed correctly.")
        } catch (e : Exception) {
            GetDbResult.Failure(e.message.toString())
        }
    }

    override suspend fun getCatProfileFromEmail(catId : String) : GetDbResult {
        return try {
            val id = Base64.encodeToString(catId.toByteArray(),0).filterNot { it == '\n' }
            val us = database.getReference("users").child(id).get().await()
            val user = us.getValue<UserProfile>() ?: return GetDbResult.Failure("Couldn't get the user correctly!")

            val sn = database.getReference("cats").child(user.catId.toString()).get().await()
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