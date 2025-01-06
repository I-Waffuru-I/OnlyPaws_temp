package com.example.onlypaws.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.onlypaws.ui.theme.OnlyPawsTheme

@Composable
fun CenteredText(
    text : String,
    modifier: Modifier = Modifier,
    fontSize : TextUnit = TextUnit.Unspecified,
    color : Color = Color.DarkGray,
    backgroundColor : Color = Color.Transparent,
    alignment: Alignment = Alignment.CenterStart,
    minLines : Int = 1,
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
    ) {
       Text(
           text = text,
           color = color,
           fontSize = fontSize,
           minLines = minLines,
           modifier = Modifier.align(alignment)
       )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TestCenter(modifier: Modifier = Modifier) {

    OnlyPawsTheme {

        CenteredText(
            text = "Test value!",
            modifier = Modifier
                .height(50.dp)
                .width(300.dp)
        )


    }

}