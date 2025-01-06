package com.example.onlypaws.models.list

sealed interface ListState  {
    data object Loading : ListState
    data object SuccessDislike : ListState
    data object SuccessLike : ListState
    data class Failure(var error : String) : ListState
}