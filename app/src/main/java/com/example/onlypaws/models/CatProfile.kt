package com.example.onlypaws.models

import kotlinx.serialization.Serializable


@Serializable
data class CatProfile (
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val image: String = "",

)
