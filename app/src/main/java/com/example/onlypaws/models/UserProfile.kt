package com.example.onlypaws.models

import kotlinx.serialization.Serializable


@Serializable
data class UserProfile (
    val accountName : String,
    val accountImageLink : String,
    val interests : List<InterestGroup>
)