package com.example.onlypaws.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.onlypaws.ui.theme.OnlyPawsTheme

@Composable
fun ValueUpdateField(
    title : String,
    value: String,
    modifier: Modifier = Modifier
) {

    Column {


        Row {
            Box(Modifier.weight(0.8f)) {
                Column {

                    Text(
                        text = title,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                            .border(width = 3.dp,color = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = value,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)
                            .padding(5.dp)
                            .border(width = 3.dp,color = MaterialTheme.colorScheme.primary)
                    )
                }

            }
            Box(Modifier.weight(0.2f)) {

                Button (
                    onClick = {},
                    colors = ButtonColors (
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.primaryContainer,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    ),
                ) {
                    Text(
                        text = "Edit",
                        color = MaterialTheme.colorScheme.primaryContainer,
                        //modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                    )
                }
            }
        }






        ConstraintLayout(
            modifier = modifier.height(75.dp).width(250.dp)
                .background(MaterialTheme.colorScheme.surfaceDim)
        ) {
            val (titleTxt, displayTxt, editBtn) = createRefs()

            Text(
                text = title,
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(titleTxt) {
                    top.linkTo(parent.top, 5.dp)
                    end.linkTo(editBtn.start, 5.dp)
                    start.linkTo(parent.start, 5.dp)
                })
            Text(
                text = value,
                fontSize = 15.sp,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .constrainAs(displayTxt) {
                        top.linkTo(titleTxt.bottom, 10.dp)
                        start.linkTo(titleTxt.start)
                        end.linkTo(editBtn.start, 5.dp)
                        bottom.linkTo(parent.bottom, 10.dp)
                    })

            Button(
                onClick = {},
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                ),
                modifier = Modifier.constrainAs(editBtn) {
                    top.linkTo(parent.top, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                }
            ) {
                Text(
                    text = "Edit",
                    color = MaterialTheme.colorScheme.primaryContainer,
                    //modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
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
            modifier = Modifier.background(Color.Cyan)
        )
    }
}