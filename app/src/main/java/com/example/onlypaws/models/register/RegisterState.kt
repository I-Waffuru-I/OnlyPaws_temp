package com.example.onlypaws.models.register

data class RegisterState (
    val username : String = "",
    val imageLink : String = "",
    val password : String = "",
    val email : String = "",
    val description : String = "",
    val errorMessage : String? = null,
    val triesToRegister : Boolean = false,
    val triesToReturnLogin : Boolean = false,
)