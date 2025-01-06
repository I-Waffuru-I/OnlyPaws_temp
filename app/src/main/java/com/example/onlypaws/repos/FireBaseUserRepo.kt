package com.example.onlypaws.repos

import android.util.Base64
import android.util.Log
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult
import com.example.onlypaws.models.registerDetails.DetailState
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await

class FireBaseUserRepo : IUserAccountRepository {

    private val database = Firebase.database

    override suspend fun getLoggedInUser(email : String): GetDbResult {
        return try {
            val id64 = Base64.encodeToString(email.toByteArray(),0).filterNot { it == '\n' }
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

    override suspend fun changeViewedId(email: String, catId: Int) : SaveDbResult {
        return try {
            if(email.isBlank()) SaveDbResult.Failure("Invalid email!")
            if(catId == -1) SaveDbResult.Failure("Invalid Cat ID : -1")

            val id = Base64.encodeToString(email.toByteArray(),0).filterNot { it == '\n' }
            database.getReference("users").child(id).child("currentViewedId").setValue(catId).await()

            SaveDbResult.Success
        } catch (e : Exception) {
            SaveDbResult.Failure(e.message.toString())
        }
    }

    override suspend fun addToLiked(email: String, catId: Int, liked: Boolean): SaveDbResult {
        return try {
            if(email.isBlank()) SaveDbResult.Failure("Invalid email!")
            if(catId == -1) SaveDbResult.Failure("Invalid Cat ID : -1")

            val id = Base64.encodeToString(email.toByteArray(),0).filterNot { it == '\n' }
            val l = if(liked) "l" else "d"
            var exists = false
            val ids = mutableListOf<Int>()

            val result = database.getReference("liked").child(id).child(l).get().await()
            result.children.mapNotNull {
                val index = it.getValue<Int>()
                index?.let {
                    if (index == catId) exists = true
                    ids.add(index)
                }
            }
            if (exists) {
                Log.e("Firebase","User has been liked already!")
                SaveDbResult.Failure("User has been liked already!")
            }

            Log.i("Firebase","Adding user to liked list")
            ids.add(catId)
            database.getReference("liked").child(id).child(l).setValue(ids).await()

            SaveDbResult.Success
        } catch (e : Exception) {
            SaveDbResult.Failure(e.message.toString())
        }
    }

    override suspend fun removeFromLiked(email: String, catId: Int, isLiked: Boolean): SaveDbResult {
        return try {
            val id = Base64.encodeToString(email.toByteArray(),0).filterNot { it == '\n' }
            val ini = if(isLiked) "l" else "d"
            val new = if(isLiked) "d" else "l"
            val l1 = mutableListOf<Int>()
            val l2 = mutableListOf<Int>()

            val initial = database.getReference("liked").child(id).child(ini).get().await()
            initial.children.mapNotNull {
                val x = it.getValue<Int>()!!
                if (catId != x )
                    l1.add(x)
            }
            database.getReference("liked").child(id).child(ini).setValue(l1).await()

            val created = database.getReference("liked").child(id).child(new).get().await()
            created.children.mapNotNull {
                l2.add(it.getValue<Int>()!!)
            }
            l2.add(catId)
            database.getReference("liked").child(id).child(new).setValue(l2).await()

            SaveDbResult.Success
        } catch (e : Exception) {
            SaveDbResult.Failure(e.message.toString())
        }
    }

    override suspend fun getAllLiked(email: String, liked : Boolean): GetDbResult {
        return try {
            if(email.isBlank()) SaveDbResult.Failure("Invalid email!")

            val id = Base64.encodeToString(email.toByteArray(),0).filterNot { it == '\n' }
            val ids = mutableListOf<Int>()
            val cats = mutableListOf<CatProfile>()

            val l = if(liked) "l" else "d"
            val result = database.getReference("liked").child(id).child(l).get().await()

            result.children.mapNotNull {
                val i = it.getValue(Int::class.java)
                i?.let {
                    ids.add(i)
                }
            }

            for(i in ids) {
                i.let {
                    val x = database.getReference("cats").child(i.toString()).get().await()
                    x.getValue<CatProfile>()?.let {
                        cats.add(it)
                    }
                }
            }
            GetDbResult.Success(cats)
        } catch (e : Exception) {
            GetDbResult.Failure(e.message.toString())
        }
    }
}