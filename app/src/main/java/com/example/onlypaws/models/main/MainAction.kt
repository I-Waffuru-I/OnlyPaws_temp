package com.example.onlypaws.models.main

sealed interface MainAction {
    data object OnLike : MainAction
    data object OnDislike : MainAction
    data object OnProfileView : MainAction
    data object OnRetry : MainAction
}