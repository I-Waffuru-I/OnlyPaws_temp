package com.example.onlypaws.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserProfile (
    val id : String = "",
    val currentViewedId : Int = 0,
    var catId : Int = -1
)