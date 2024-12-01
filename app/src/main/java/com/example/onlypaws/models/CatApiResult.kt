package com.example.onlypaws.models

import kotlinx.serialization.Serializable

@Serializable
data class CatApiResult (
    val id : String,
    val url : String,
    val width : Int,
    val height : Int,
)