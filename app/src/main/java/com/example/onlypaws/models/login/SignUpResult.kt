package com.example.onlypaws.models.login

sealed interface SignUpResult {
    data class Success(val username : String) : SignUpResult
    data object Cancelled : SignUpResult
    data class Failure(val error : String) : SignUpResult
}