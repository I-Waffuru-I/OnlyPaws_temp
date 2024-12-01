package com.example.onlypaws.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserProfile (
    val id : String = "",
    val accountName : String = "",
    val accountImageLink : String = "",
    val description : String = "",
    val currentViewedId : Int = 0,
){

}
