package com.example.onlypaws.repos

import android.util.Base64
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult
import com.example.onlypaws.models.registerDetails.DetailState
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class FireBaseUserRepo : IUserAccountRepository {

    private val database = Firebase.database

    override suspend fun getLoggedInUser(id : String): GetDbResult {

        return try {
            val id64 = Base64.encodeToString(id.toByteArray(),0).filterNot { it == '\n' }
            val sn = database.getReference("users").child(id64).get().await()
            val usr : UserProfile? = sn.getValue(UserProfile::class.java)
            if(usr != null) GetDbResult.Success(usr)
            else GetDbResult.Failure("User info couldn't be parsed correctly.")
        } catch (e: Exception) {
            GetDbResult.Failure(e.message.toString())
        }
    }

    override suspend fun tryRegisterUser(details : DetailState) : SaveDbResult {
        return try {
            val id = Base64.encodeToString(details.email.toByteArray(),0).filterNot { it == '\n' }
            val us = database.getReference("user_ids").get().await()
            // self evident
            var exists = false
            // increments for every account, meaning it'll be a new one once every user is accounted for.
            var accCount = 0
            for(u in us.children) {
                u.getValue(String::class.java).let {
                    accCount += 1
                    if (it == id) {
                        exists = true
                    }
                }
            }
            if(exists) return SaveDbResult.Failure("User exists already!")
            val cat = CatProfile(
                id = accCount,
                name = details.name,
                description = details.description,
                image = details.imageLink
            )
            val profile = UserProfile(
                id = details.email,
                currentViewedId = 0,
                    catId = accCount
            )
            // save user account info to 'users'
            database.getReference("users").child(id).setValue(profile).await()
            // save user id to 'usr_ids'
            database.getReference("user_ids").child(accCount.toString()).setValue(id).await()
            //save cat profile
            database.getReference("cats").child(accCount.toString()).setValue(cat).await()
            SaveDbResult.Success
        } catch (e : Exception) {
            SaveDbResult.Failure(e.message.toString())
        }
    }

    override suspend fun doesUserExist(email: String) : Boolean {
        val id = Base64.encodeToString(email.toByteArray(),0).filterNot { it == '\n' }

        val us = database.getReference("user_ids").get().await()
        var exists = false
        for(u in us.children) {
            u.getValue(String::class.java).let {
                if (it == id) {
                    exists = true
                }
            }
        }
        return exists
    }
}