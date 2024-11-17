package com.example.onlypaws.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogButton(
    text : String,
    onClick : ()->Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            colors = ButtonColors (
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.inversePrimary,
                disabledContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
            )

        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogButtonPreview(

) {

    Column{

        LogButton("Meow in!",{})
        LogButton("Meow out!",{})
    }
}