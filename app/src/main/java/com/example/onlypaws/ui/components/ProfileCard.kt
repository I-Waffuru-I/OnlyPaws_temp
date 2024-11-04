package com.example.onlypaws.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.example.onlypaws.R
import com.example.onlypaws.models.CatProfile

@Composable
fun ProfileCard(
    likeClick: () -> Unit,
    dislikeClick: () -> Unit,
    cat : CatProfile,
) {
/*

    Column (
        modifier = Modifier.fillMaxWidth()
            .background(colorResource(R.color.main_background_2))
            .border(2.dp,colorResource(R.color.outline_1))
        ){

        Image(
            painter = rememberAsyncImagePainter(cat.image),
            contentDescription = cat.description,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = cat.name,
            fontSize = 25.sp,
            color = colorResource(R.color.main_text),
            textAlign = TextAlign.Left

        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = cat.description,
            fontSize = 25.sp,
            color = colorResource(R.color.main_text),
            textAlign = TextAlign.Left

        )
    }

    Row (horizontalArrangement = Arrangement.SpaceBetween){

        Button(
            onClick = dislikeClick,
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .alpha(0.1f)
        ){
        }
        Button(
            onClick = likeClick,
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .alpha(0.1f)
        ){
        }
    }
    */


    ConstraintLayout (
        modifier = Modifier.fillMaxWidth()
            .background(colorResource(R.color.main_background_2))
            .border(2.dp,colorResource(R.color.outline_1))
    ){

        val(catImg,catName,catDescription, dislikeBtn,likeBtn) = createRefs()
        Image(
            painter = rememberAsyncImagePainter(cat.image),
            contentDescription = cat.description,
            modifier = Modifier.constrainAs(catImg) {
                top.linkTo(parent.top)
            }
                .fillMaxSize()
        )
        Text(
            modifier = Modifier.constrainAs(catDescription){
                start.linkTo(parent.start)
                bottom.linkTo(catImg.bottom)

            },
            text = cat.description,
            fontSize = 15.sp,
            color = colorResource(R.color.main_text)
        )
        Text(
            modifier = Modifier.constrainAs(catName){
                start.linkTo(catDescription.start)
                bottom.linkTo(catImg.top)
            },
            text = cat.name,
            fontSize = 50.sp,
            color = colorResource(R.color.main_text)
        )

        Button(
            onClick = dislikeClick,
            modifier = Modifier.constrainAs(dislikeBtn) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)

            }
                .fillMaxHeight()
                .alpha(0.1f)
        ){
        }
        Button(
            onClick = likeClick,
            modifier = Modifier.constrainAs(likeBtn) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
                .fillMaxHeight()
                .alpha(0.1f)
        ){
        }
    }

}


@Preview
@Composable
fun ProfileCardPreview()
{
    val cat = CatProfile(
        1,
        "bob",
        "bobbilicious cat",
        "https://media.discordapp.net/attachments/1298965031366561792/1300939532073435167/AP1GczPOHchuf9ZlmaQol3lF_JS7KHDxegh_L8uIpRg375a2aqLF-T-wiOR8w713-h950-s-no-gm.png?ex=6722a9da&is=6721585a&hm=0d0bada2e398ded094f9652123c570aaf318bb0940bb87b443067754292d62e4&=&format=webp&quality=lossless&width=263&height=350"
    )
   ProfileCard({},{},cat)
}