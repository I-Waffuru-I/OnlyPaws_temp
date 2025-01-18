package com.example.onlypaws.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.example.onlypaws.R
import com.example.onlypaws.ui.theme.OnlyPawsTheme


@Composable
fun PasswordField(
    password : String,
    onValueChange : (String)->Unit,
    modifier: Modifier = Modifier,
    fontSize : TextUnit = TextUnit.Unspecified,
    color : Color = Color.DarkGray,
    backgroundColor : Color = Color.Transparent,
    iconColor : Color = Color.DarkGray,
    alignment: Alignment = Alignment.CenterStart,
    label : @Composable (()->Unit)? = null,
    placeholder : @Composable (()->Unit)? = null,
    minLines : Int = 1,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier
            .background(backgroundColor)
    ) {
        TextField(
            value = password,
            onValueChange = onValueChange,
            textStyle = TextStyle(color = color, fontSize = fontSize),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,

            ),
            minLines = minLines,
            label = label,
            placeholder = placeholder,
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft
                else Icons.Filled.KeyboardArrowDown

                val description = if (passwordVisible) R.string.comp_hide_pswd else R.string.comp_show_pswd

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(
                        imageVector  = image,
                        stringResource(description),
                        tint = iconColor
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .align(alignment)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaswdFieldPreview(modifier: Modifier = Modifier) {


    OnlyPawsTheme {

        var pswd by remember {mutableStateOf("bou")}
        PasswordField(password = pswd, onValueChange = {pswd = it})

    }


}