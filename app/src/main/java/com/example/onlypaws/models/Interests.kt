package com.example.onlypaws.models

import kotlinx.serialization.Serializable

@Serializable
data class InterestGroup (
    val id : String,
    val title : String,
    val values : List<String>
)