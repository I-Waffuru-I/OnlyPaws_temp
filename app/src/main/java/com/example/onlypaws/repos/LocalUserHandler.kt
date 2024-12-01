package com.example.onlypaws.repos

import android.content.Context
import com.example.onlypaws.models.UserProfile
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File




/*

    NOT IN USE

 */

class LocalUserHandler(context : Context) {
    private var file : File = File(context.filesDir, "user_data.json")


    fun serializeUserToJson(userData: UserProfile) {
        try {
            val jsonString = Json.encodeToString(userData)
            file.writeText(jsonString)
        } catch (e: Exception) {
            error("Failed to serialize user:\r\n" + e.message)
        }
    }


    fun deserializeJsonToUser() : UserProfile? {
        try {
            val str = file.readText()
            val user:  UserProfile = Json.decodeFromString(str)
            return user
        } catch (e : Exception){
            return null
        }
    }

    fun clearUserSaveFile() {
        try {
            file.writeText("")
        } catch (e : Exception){
            error("Failed to clear save file!\r\n" + e.message)
        }
    }
}