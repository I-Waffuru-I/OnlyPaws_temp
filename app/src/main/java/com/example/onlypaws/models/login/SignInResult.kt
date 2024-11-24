package com.example.onlypaws.models.login

sealed interface SignInResult {
    data class Success(val username : String) : SignInResult
    data object Cancelled : SignInResult
    data class Failure(val error : String) : SignInResult
    data object NoCredentials : SignInResult
}