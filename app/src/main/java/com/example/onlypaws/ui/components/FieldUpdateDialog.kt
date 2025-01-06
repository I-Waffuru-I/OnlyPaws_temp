package com.example.onlypaws.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun FieldUpdateDialog(
    value : String,
    onDismissRequest : ()->Unit,
    onUpdateText : (String)->Unit,
    onSaveValue : (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = modifier,
        ){

            Column {

                TextField(
                    value = value,
                    onValueChange = {
                        onUpdateText(it)
                    }
                )

                Button(
                    onClick = {
                        onSaveValue(value)
                    }
                ) {

                }
            }
        }
    }
}