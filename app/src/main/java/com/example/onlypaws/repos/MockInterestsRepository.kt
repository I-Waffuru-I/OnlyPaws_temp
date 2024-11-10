package com.example.onlypaws.repos

import com.example.onlypaws.models.InterestGroup

class MockInterestsRepository : IInterestsRepository {
    private val interests : List<InterestGroup> = listOf(
        InterestGroup(
            id = "activity_location_preference",
            title = "Interior / Exterior?",
            values = listOf("Indoor","Outdoor")
        ),
        InterestGroup(
            id="get_food_tactics",
            title = "Getting Food",
            values = listOf("Hunt","Lazy","Make noises","Playing near bowl")
        ),
        InterestGroup(
            id="preferred_food",
            title = "Favourite Food",
            values = listOf("Chicken","Beef","Pork","Veggie")
        ),
        InterestGroup(
            id = "pass_times",
            title = "Activities",
            values = listOf("Playing with some plastic", "Playing with toys", "Catching mice", "Sleeping", "Running between people's legs")
        )
    )



    override suspend fun getAllInterests() : List<InterestGroup> {
        return interests
    }

    override suspend fun getInterestFromTitle(title: String) : InterestGroup? {
        var inter : InterestGroup? = null

        interests.forEach {
            ig ->
                if(ig.id == title){
                    inter = ig
                }
        }
        return inter
    }
}