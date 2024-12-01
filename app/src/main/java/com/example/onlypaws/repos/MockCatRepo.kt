package com.example.onlypaws.repos

import android.app.Application
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.db.GetDbResult

class MockCatRepo(application: Application) : ICatRepository {

    private var _currentCatIndex : Int = -1

    private val cats : List<CatProfile> = listOf(
        CatProfile(0,"first cat","very cool cat","https://media.discordapp.net/attachments/1147544245591359618/1300767160053927936/20230819_101127.jpg?ex=67220952&is=6720b7d2&hm=f64dd227d419b8b78a32eac6dd2373fc9b3d51dec9240ce73724e4a64a95a50e&=&format=webp&width=503&height=671"),
        CatProfile(1,"second cat","less cool, but still cool", "https://media.discordapp.net/attachments/1147544245591359618/1300767188944420894/20230811_190454.jpg?ex=67220958&is=6720b7d8&hm=b868ae5046afc3568c931f10819fac4d372c80217bc54b0c1aae35453b0b51b6&=&format=webp&width=503&height=671"),
        CatProfile(2,"3rd cat","same image as first because bleh","https://media.discordapp.net/attachments/1147544245591359618/1300767160053927936/20230819_101127.jpg?ex=67220952&is=6720b7d2&hm=f64dd227d419b8b78a32eac6dd2373fc9b3d51dec9240ce73724e4a64a95a50e&=&format=webp&width=503&height=671"),
        CatProfile(3,"last cat","probably the coolest one","https://media.discordapp.net/attachments/1298965031366561792/1300893228249124985/AP1GczNwamBesqfwpJPp49wbM18sjCW1XIe3dYv604VFaXtVKKknc4nLu2ZtDww713-h951-s-no-gm.png?ex=672478fb&is=6723277b&hm=2723da445b569b76884278f325dc5b513f2e45c6218553bd790d08b0b6a5887d&=&format=webp&quality=lossless&width=503&height=671"),

    )

    override suspend fun getCatProfiles(): GetDbResult {
        return GetDbResult.Success(cats)
    }

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