package com.example.onlypaws.services

import android.util.Base64
import android.util.Log
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.SaveDbResult
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class FireBaseService {

    private var database : DatabaseReference = Firebase.database.reference

    fun saveUserToDb(profile : UserProfile) : SaveDbResult {
        return try {
            val id64 = Base64.encodeToString(profile.id.toByteArray(),0)
            database.child("users").child(id64).setValue(profile)
            SaveDbResult.Success
        } catch (e : Exception) {
            SaveDbResult.Failure(e.message.toString())
        }
    }

    fun getUserFromDb(id : String): UserProfile? {
        var usr : UserProfile? = null
        val id64 = Base64.encodeToString(id.toByteArray(),0)
        database.child("users").child(id64).get().addOnSuccessListener {
            Log.i("Firebase","Got user with id : [ $id ] and value [\r\n ${it.value}\r\n ]")
        }.addOnFailureListener {
            Log.e("Firebase","User wasn't properly gotten! ",it)
        }

        return usr
    }

}