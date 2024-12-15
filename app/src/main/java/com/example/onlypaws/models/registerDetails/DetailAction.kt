package com.example.onlypaws.models.registerDetails

import com.example.onlypaws.models.register.SignUpResult

sealed interface DetailAction {
    data class OnChangeName(val name : String) : DetailAction
    data class OnChangeDescription(val description : String) : DetailAction
    data class OnImageLinkChange(val link : String) : DetailAction
    data object OnImageRandomize : DetailAction
    data class OnTrySignUp(val result : SignUpResult) : DetailAction
    data object OnBackSignUp : DetailAction
}