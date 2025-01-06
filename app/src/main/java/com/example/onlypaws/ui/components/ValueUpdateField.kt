package com.example.onlypaws.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlypaws.ui.theme.OnlyPawsTheme

@Composable
fun ValueUpdateField(
    title : String,
    value: String,
    onEditPress : (String)->Unit,
    modifier: Modifier = Modifier,
    minLines : Int = 1,
) {

    var storedValue : String by remember { mutableStateOf(value)}
    Column (
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
    ) {

        CenteredText(
            text = title,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
        )
        Row {

            CenteredTextField (
                text = storedValue,
                onValueChange = { storedValue = it },
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary,
                minLines = minLines,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .weight(0.8f)
                    .padding(start = 10.dp)
            )

            IconButton(
                onClick = { onEditPress(storedValue) },
                modifier = Modifier
                    .weight(0.2f)
            ) {

                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.primaryContainer,
                )

            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ValueTest () {
    OnlyPawsTheme {

        ValueUpdateField(
            title = "Username",
            value = "Matha verb",
            onEditPress = {},
            modifier = Modifier.background(Color.Cyan)
        )
    }
}