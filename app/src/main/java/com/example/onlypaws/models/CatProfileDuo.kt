package com.example.onlypaws.models

// First cat is shown, second cat is loaded
data class CatProfileDuo (
    val firstCat : CatProfile,
    val lastCat : CatProfile,
)