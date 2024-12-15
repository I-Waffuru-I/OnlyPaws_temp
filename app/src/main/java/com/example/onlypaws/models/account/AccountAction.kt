package com.example.onlypaws.models.account

sealed interface AccountAction {
    data object OnRetry : AccountAction
    data object OnLogOut : AccountAction
}