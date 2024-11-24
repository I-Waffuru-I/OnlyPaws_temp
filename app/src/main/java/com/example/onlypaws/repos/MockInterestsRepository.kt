package com.example.onlypaws.repos

import com.example.onlypaws.models.InterestGroup

class MockInterestsRepository : IInterestsRepository {
    private val interests : List<InterestGroup> = listOf(
        InterestGroup(
            id = 0,
            title = "Interior / Exterior?",
            values = listOf("Indoor","Outdoor")
        ),
        InterestGroup(
            id=1,
            title = "Getting Food",
            values = listOf("Hunt","Lazy","Make noises","Playing near bowl")
        ),
        InterestGroup(
            id=2,
            title = "Favourite Food",
            values = listOf("Chicken","Beef","Pork","Veggie")
        ),
        InterestGroup(
            id = 3,
            title = "Activities",
            values = listOf("Playing with some plastic", "Playing with toys", "Catching mice", "Sleeping", "Running between people's legs")
        ),
        InterestGroup(
            id = 4,
            title = "Favourite Sleeping Place",
            values = listOf("On the human","On the ground","On bed","In the dresser","In a box","On the table")
        )
    )



    override suspend fun getAllInterests() : List<InterestGroup> {
        return interests
    }

    override suspend fun getInterestFromTitle(title: String) : InterestGroup? {
        interests.forEach {
            ig ->
                if(ig.title == title){
                    return ig
                }
        }
        return null
    }

    override suspend fun getInterestFromId(interestId: Int): InterestGroup? {
        interests.forEach {
            ig ->
                if(ig.id == interestId){
                    return ig
                }
        }
        return null
    }
}