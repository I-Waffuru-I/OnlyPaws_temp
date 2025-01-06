package com.example.onlypaws.models.profile

import com.example.onlypaws.models.CatProfile

sealed class ProfileState {
    data class Success(val cat : CatProfile) : ProfileState()
    data object Loading : ProfileState()
    data class Failure(val error : String) : ProfileState()
}
