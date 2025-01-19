package com.example.onlypaws.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ListItemDisplay(
    title : String,
    description : String,
    image : Any?,
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
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.4f)
            )
            CenteredText(
                text = description,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.6f)
            )
        }

        IconButton(
            onClick = onToggle,
            modifier = Modifier
                .weight(0.2f)
        ) {
            val ic = if(isAdded) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder
            var t = if(isAdded) "Remove " else "Add "
            t += title
            Icon(
                imageVector = ic,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = t
            )
        }
    }
}