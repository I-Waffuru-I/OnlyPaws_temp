package com.example.onlypaws.repos

import android.app.Application
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.db.GetDbResult

class MockCatRepo(application: Application) : ICatRepository {

    private var _currentCatIndex : Int = -1

    private val cats : List<CatProfile> = listOf(
        CatProfile(0,"first cat","very cool cat","https://cdn2.thecatapi.com/images/MjAxMjkwMg.jpg"),
        CatProfile(1,"second cat","less cool, but still cool", "https://cdn2.thecatapi.com/images/aup.jpg"),
        CatProfile(2,"3rd cat","same image as first because bleh","https://cdn2.thecatapi.com/images/dmj.jpg"),
        CatProfile(3,"last cat","probably the coolest one","https://cdn2.thecatapi.com/images/d0q.jpg"),

    )


    override suspend fun getCatProfile(catId: Int): GetDbResult {
        for(cat in cats){
            if(cat.id == catId)
                return GetDbResult.Success(cat)
        }
        return GetDbResult.Failure("Couldn't get the cat!")
    }

    override suspend fun getLastCatProfile(): GetDbResult {
        return getCatProfile(_currentCatIndex)
    }

    override suspend fun getSuggestedCatProfile(): GetDbResult {
        _currentCatIndex += 1
        return getCatProfile(_currentCatIndex)
    }

}