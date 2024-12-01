package com.example.onlypaws.models.login

data class LoginState (
    val loggedInUser : String? = null,
    var username : String = "user",
    var password : String = "pass",
    var errorMessage : String? = null,
    var triesToLogIn : Boolean = false,
    var triesToRegister : Boolean = false,
)