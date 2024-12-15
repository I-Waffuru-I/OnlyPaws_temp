package com.example.onlypaws.models.register

data class RegisterState (
    val username : String = "",
    val imageLink : String = "",
    val password : String = "",
    val email : String = "",
    val description : String = "",
    val errorMessage : String? = null,
    val canSignUp : Boolean = false,
    var triesToRegister : Boolean = false,
    var triesToReturnLogin : Boolean = false,
)