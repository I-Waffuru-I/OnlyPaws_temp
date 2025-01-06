package com.example.onlypaws.models.account

sealed interface AccountAction {
    data object OnRetry : AccountAction
    data object OnLogOut : AccountAction
    data class OnSaveUsername(val name : String) : AccountAction
    data class OnSaveDescription(val description : String) : AccountAction
    data class OnSaveImageLink(val link : String) : AccountAction
}