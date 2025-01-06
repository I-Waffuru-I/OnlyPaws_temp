package com.example.onlypaws.models.routes

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
data class RegisterDetail(
    val user : String,
    val password : String
)

@Serializable
object Main

@Serializable
data class Profile(val cat : Int)

@Serializable
object Favorites

@Serializable
object Account
