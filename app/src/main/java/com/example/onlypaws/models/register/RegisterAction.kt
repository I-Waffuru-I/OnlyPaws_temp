package com.example.onlypaws.models.register

sealed interface RegisterAction {
    data object OnBackSignIn : RegisterAction
    data object OnRegister : RegisterAction
    data class OnPasswordChange(val password : String) : RegisterAction
    data class OnEmailChange(val email : String) : RegisterAction
}