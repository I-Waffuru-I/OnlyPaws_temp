package com.example.onlypaws.repos

import com.example.onlypaws.models.InterestGroup

interface IInterestsRepository {
    suspend fun getAllInterests() : List<InterestGroup>

    suspend fun getInterestFromTitle(title : String) : InterestGroup?

    suspend fun getInterestFromId(interestId : Int) : InterestGroup?
}