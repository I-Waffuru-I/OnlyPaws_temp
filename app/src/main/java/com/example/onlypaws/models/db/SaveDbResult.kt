package com.example.onlypaws.models.db

sealed interface SaveDbResult {

    data object Success : SaveDbResult
    data class Failure(val error : String) : SaveDbResult

}
