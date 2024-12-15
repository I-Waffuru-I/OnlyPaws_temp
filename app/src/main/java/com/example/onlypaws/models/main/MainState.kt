package com.example.onlypaws.models.main

import com.example.onlypaws.models.CatProfile

sealed class MainState {
    data class Success(var cat : CatProfile) : MainState()
    data class Failure(val error : String) : MainState()
    data object Loading : MainState()
    data class ViewProfile(var cat : CatProfile) : MainState()
}

