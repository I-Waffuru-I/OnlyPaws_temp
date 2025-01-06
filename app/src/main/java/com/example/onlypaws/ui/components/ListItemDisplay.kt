package com.example.onlypaws.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ListItemDisplay(
    title : String,
    description : String,
    image : String,
    onToggle : ()->Unit,
    isAdded : Boolean,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceBright)
    ) {
        AsyncImage(
            model = image,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .weight(0.4f)
                .fillMaxHeight()
        )

        Column (
            modifier = Modifier
                .weight(0.4f)
        ) {

            CenteredText(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 25.sp,
            )
            CenteredText(
                text = description,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 15.sp,
            )
        }

        IconButton(
            onClick = onToggle,
            modifier = Modifier
                .weight(0.2f)
        ) {
            val ic = if(isAdded) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder
            val t = if(isAdded) "Remove" else "Add"
            Icon(
                imageVector = ic,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = t
            )
        }
    }
}
/*

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun prevCatItem(modifier: Modifier = Modifier) {
    val c1 = CatProfile(
        id = 0,
        name = "Bob",
        description = "bobby",
        image = "https://cdn2.thecatapi.com/images/MjAxMjkwMg.jpg"
    )
    val c2 = CatProfile(
        id= 1,
        description="less cool, but still cool",
        image="https://cdn2.thecatapi.com/images/aup.jpg",
        name= "second cat"
    )

    OnlyPawsTheme {
        Column {
            ListItemDisplay(
                title = c1.name,
                image = c1.image,
                isAdded = true,
                onToggle = {}
            )
            ListItemDisplay(
                title = c2.name,
                image = c2.image,
                isAdded = false,
                onToggle = {}
            )
        }
    }

}
 */