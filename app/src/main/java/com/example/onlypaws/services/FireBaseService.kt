package com.example.onlypaws.services

import android.util.Base64
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.SaveDbResult
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FireBaseService {

    private var database = Firebase.database

    suspend fun saveUserToDb(profile : UserProfile) : SaveDbResult {
        return try {
            val id64 = Base64.encodeToString(profile.id.toByteArray(),0).filterNot { it == '\n'}
            database.getReference("users").child(id64).setValue(profile).await()
            SaveDbResult.Success
        } catch (e : Exception) {
            SaveDbResult.Failure(e.message.toString())
        }
    }

    suspend fun getUserFromDb(id : String): UserProfile? {

        val id64 = Base64.encodeToString(id.toByteArray(),0).filterNot { it == '\n' }

        return suspendCancellableCoroutine {
            continuation ->
            val ref = database.getReference("users").child(id64)

            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserProfile::class.java)
                    continuation.resume(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }
}