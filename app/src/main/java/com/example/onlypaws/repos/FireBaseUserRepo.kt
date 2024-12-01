package com.example.onlypaws.repos

import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult
import com.example.onlypaws.services.FireBaseService

class FireBaseUserRepo : IUserAccountRepository {

    private var serv = FireBaseService()

    override suspend fun getLoggedInUser(id : String): GetDbResult {
        return try {
            val profile = serv.getUserFromDb(id)
            if(profile != null){
                GetDbResult.Success(profile)
            } else {
                GetDbResult.Failure("No user found!")
            }
        } catch (e : Exception) {
           GetDbResult.Failure(e.message.toString())
        }
    }

    override suspend fun tryRegisterUser(profile : UserProfile) : SaveDbResult {
        return try {
            serv.saveUserToDb(profile)
            SaveDbResult.Success
        } catch (e : Exception) {
            SaveDbResult.Failure(e.message.toString())
        }
    }
}