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
    displayDetails : (Int)->Unit,
    cat : CatProfile,
) {


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
                top.linkTo(catImg.top)
            },
            text = cat.name,
            fontSize = 50.sp,
            color = colorResource(R.color.main_text)
        )

        Row (

        ) {

            Button(
                onClick = dislikeClick,
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(0f)
                    .weight(0.2f)
            ){
            }
            Button(
                onClick = { displayDetails(cat.id) },
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(0f)
                    .weight(0.6f)
            ){}
            Button(
                onClick = likeClick,
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(0f)
                    .weight(0.2f)
            ){
            }
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
   ProfileCard({},{},{},cat)
}