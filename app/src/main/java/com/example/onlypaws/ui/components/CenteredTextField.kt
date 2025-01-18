package com.example.onlypaws.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.onlypaws.ui.theme.OnlyPawsTheme

@Composable
fun CenteredTextField(
    text : String,
    onValueChange : (String)->Unit,
    modifier: Modifier = Modifier,
    fontSize : TextUnit = TextUnit.Unspecified,
    label : @Composable (()->Unit)? = null,
    placeholder : @Composable (()->Unit)? = null,
    color : Color = Color.DarkGray,
    backgroundColor : Color = Color.Transparent,
    alignment: Alignment = Alignment.CenterStart,
    minLines : Int = 1,
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
    ) {
       TextField(
           value = text,
           onValueChange = onValueChange,
           textStyle = TextStyle(color = color, fontSize = fontSize),
           label = label,
           colors = TextFieldDefaults.colors(
               unfocusedContainerColor = Color.Transparent,
               focusedContainerColor = Color.Transparent,
           ),
           placeholder = placeholder,
           minLines = minLines,
           modifier = Modifier
               .align(alignment)
               .fillMaxWidth()
       )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TestCenterField(modifier: Modifier = Modifier) {

    OnlyPawsTheme {

        CenteredTextField(
            text = "Test value!",
            onValueChange = {},
            modifier = Modifier
                .height(50.dp)
                .width(300.dp)
        )


    }

}