package com.example.onlypaws.models

import com.example.onlypaws.models.routes.Favorites
import kotlinx.serialization.Serializable

@Serializable
data class InterestGroup (
    val id : Int,
    val title : String,
    val values : List<String>
)


val USER_INTERESTS : Map<String,InterestGroup> =  mapOf(
    Pair("interior",InterestGroup(
        id = 0,
        title = "Interior / Exterior?",
        values = listOf("Indoor","Outdoor")
    )),
    Pair("get_food", InterestGroup(
        id=1,
        title = "Getting Food",
        values = listOf("Hunt","Lazy","Make noises","Playing near bowl")
    )),
    Pair("fav_food", InterestGroup(
        id=2,
        title = "Favourite Food",
        values = listOf("Chicken","Beef","Pork","Veggie")
    )),
    Pair("activities",InterestGroup(
        id = 3,
        title = "Activities",
        values = listOf("Playing with some plastic", "Playing with toys", "Catching mice", "Sleeping", "Running between people's legs")
    )),
    Pair("sleep",InterestGroup(
        id = 4,
        title = "Favourite Sleeping Place",
        values = listOf("On the human","On the ground","On bed","In the dresser","In a box","On the table")
    ))
)
