package com.example.onlypaws.models.db

sealed interface GetDbResult {
    data class Success(val value : Any) : GetDbResult
    data class Failure(val error : String) : GetDbResult
}