package com.example.onlypaws.models.register

sealed interface RegisterAction {
    data object OnSignIn : RegisterAction
    data class OnRegister(val result : SignUpResult) : RegisterAction
    data class OnUsernameChange(val username : String) : RegisterAction
    data class OnPasswordChange(val password : String) : RegisterAction
    data class OnEmailChange(val email : String) : RegisterAction
    data class OnImageLinkChange(val link : String) : RegisterAction
    data object OnImageRandomize : RegisterAction
}