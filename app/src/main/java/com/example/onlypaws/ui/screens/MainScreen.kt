package com.example.onlypaws.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.onlypaws.R
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.main.MainAction
import com.example.onlypaws.models.main.MainState
import com.example.onlypaws.models.main.MainStateList


@Composable
fun MainScreen (
    onAction : (MainAction)->Unit,
    mainScreenUiState: MainState,
    displayDetails: (Int) -> Unit,
    modifier : Modifier = Modifier,
){

    LaunchedEffect(key1 = mainScreenUiState.view) {
        if(mainScreenUiState.view && mainScreenUiState.cat != null)
            displayDetails(mainScreenUiState.cat!!.id)

    }


    when(mainScreenUiState.state) {
        is MainStateList.Loading -> {
            LoadingMain(modifier)
        }
        is MainStateList.Failure -> {
            ErrorMain(
                modifier = modifier,
                retryAction = { onAction(MainAction.OnRetry) }
            )
        }
        is MainStateList.Success -> {
            mainScreenUiState.cat?.let {
                SuccessMain(
                    cat = mainScreenUiState.cat!!,
                    onAction = onAction,
                )

            }
        }
    }
}

@Composable
fun LoadingMain(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading_cat_profile_list),
        modifier = modifier
    )
}
@Composable
fun ErrorMain(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.error_getting_cat_list)
        )

        Text(
            text = stringResource(R.string.error_getting_cat_list),
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = retryAction
        ){
            Text(
                text = stringResource(R.string.refresh_cat_profile_list)
            )
        }
    }
}

@Composable
fun SuccessMain(
    cat : CatProfile,
    onAction: (MainAction) -> Unit,
    modifier:Modifier = Modifier,
){


    ConstraintLayout (
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){

        val(catImg,catName,catDescription) = createRefs()

        // NAME
        Box(

            modifier = Modifier.constrainAs(catName){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart

        ){
            Text(
                text = cat.name,
                fontSize = 50.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(15.dp)
            )
        }

        // IMAGE
        AsyncImage(
            model = cat.image,
            contentDescription = cat.description,
            modifier = Modifier.constrainAs(catImg) {
                top.linkTo(catName.bottom)
            }
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        // DESCRIPTION
        Box(

            modifier = Modifier.constrainAs(catDescription){
                start.linkTo(catName.start)
                top.linkTo(catImg.bottom)
                bottom.linkTo(parent.bottom)
            }
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart

        ){
            Text(
                text = cat.description,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

        // BUTTONS
        Row (

        ) {

            Button(
                onClick = { onAction(MainAction.OnDislike) },
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(0f)
                    .weight(0.2f)
            ){
            }
            Button(
                onClick = {onAction(MainAction.OnProfileView)},
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(0f)
                    .weight(0.6f)
            ){}
            Button(
                onClick = { onAction(MainAction.OnLike) },
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(0f)
                    .weight(0.2f)
            ){
            }
        }
    }
}