package com.example.onlypaws.models.registerDetails

data class DetailState (
    var name : String = "",
    var imageLink : String = "",
    var description : String = "",
    var password : String = "",
    var email : String = "",
    var canSignUp : Boolean = false,
    var triesToReturnLogin : Boolean = false,
    var triesToRegister : Boolean = false,
)