package com.example.onlypaws.repos

import android.app.Application
import com.example.onlypaws.models.CatProfile

class MockCatRepo(application: Application) : ICatRepository {


    private val cats : List<CatProfile> = listOf(
        CatProfile(0,"first cat","very cool cat","https://media.discordapp.net/attachments/1147544245591359618/1300767160053927936/20230819_101127.jpg?ex=67220952&is=6720b7d2&hm=f64dd227d419b8b78a32eac6dd2373fc9b3d51dec9240ce73724e4a64a95a50e&=&format=webp&width=503&height=671"),
        CatProfile(1,"another cat","less cool, but still cool", "https://media.discordapp.net/attachments/1147544245591359618/1300767188944420894/20230811_190454.jpg?ex=67220958&is=6720b7d8&hm=b868ae5046afc3568c931f10819fac4d372c80217bc54b0c1aae35453b0b51b6&=&format=webp&width=503&height=671"),
        CatProfile(2,"last cat","probably the coolest one","https://media.discordapp.net/attachments/1147544245591359618/1300767160053927936/20230819_101127.jpg?ex=67220952&is=6720b7d2&hm=f64dd227d419b8b78a32eac6dd2373fc9b3d51dec9240ce73724e4a64a95a50e&=&format=webp&width=503&height=671")
    )

    override suspend fun getCatProfiles(): List<CatProfile> {
        return cats
    }

    override suspend fun getCatProfile(catId: Int): CatProfile {
        var c  = CatProfile(-1,"","","")
        for(cat in cats){
            if(cat.id == catId)
                c = cat
        }
        return c
    }

}