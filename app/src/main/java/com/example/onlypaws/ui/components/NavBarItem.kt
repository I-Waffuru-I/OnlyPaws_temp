package com.example.onlypaws.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavBarItem (
    onClick : () -> Unit,
    title : String,
){

    Text(
        text = title,
        modifier = Modifier.clickable { onClick }
    )
}