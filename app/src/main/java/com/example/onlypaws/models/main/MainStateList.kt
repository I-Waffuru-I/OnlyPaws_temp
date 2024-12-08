package com.example.onlypaws.models.main

import com.example.onlypaws.models.CatProfile

sealed class MainStateList {
    data object Success : MainStateList()
    data class Failure(val error : String) : MainStateList()
    data object Loading : MainStateList()
}

data class MainState (
    var cat : CatProfile? = null,
    val view : Boolean = false,
    var state : MainStateList = MainStateList.Loading
)
