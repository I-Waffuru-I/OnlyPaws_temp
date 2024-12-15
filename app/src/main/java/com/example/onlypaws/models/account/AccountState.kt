package com.example.onlypaws.models.account

import com.example.onlypaws.models.CatProfile

sealed interface AccountStateList {
    data class Success(val user : CatProfile) : AccountStateList
    data object Loading : AccountStateList
    data class Failure(val error : String) : AccountStateList
    data object LogOut : AccountStateList
}