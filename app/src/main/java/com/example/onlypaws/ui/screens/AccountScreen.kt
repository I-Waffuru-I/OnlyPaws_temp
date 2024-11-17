package com.example.onlypaws.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.onlypaws.R
import com.example.onlypaws.ui.components.LogButton
import com.example.onlypaws.ui.theme.OnlyPawsTheme

@Composable
fun AccountScreen(

){
    Column(
        modifier = Modifier

    ) {

        Text(text = "Account page")
        LogButton(stringResource(R.string.log_in_button),{})
        LogButton(stringResource(R.string.log_out_button),{})
    }
}