package com.example.onlypaws.models.list

sealed interface ListAction {
    data class OnToggleLiked(val catId : Int) : ListAction
    data object OnRefresh : ListAction
    data object OnSwitchDisplayed : ListAction
}