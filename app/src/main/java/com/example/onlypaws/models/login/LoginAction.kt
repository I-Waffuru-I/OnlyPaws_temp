package com.example.onlypaws.models.login

sealed interface LoginAction {
    data object OnSignUp : LoginAction
    data class OnSignIn(val result : SignInResult) : LoginAction
}