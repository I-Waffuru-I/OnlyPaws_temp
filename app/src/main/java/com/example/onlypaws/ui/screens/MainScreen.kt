package com.example.onlypaws.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.onlypaws.ui.components.CenteredText
import com.example.onlypaws.ui.components.LottieLoading


@Composable
fun MainScreen (
    onAction : (MainAction)->Unit,
    mainScreenUiState: MainState,
    displayDetails: (Int) -> Unit,
    modifier : Modifier = Modifier,
){
    when(mainScreenUiState) {
        is MainState.Loading -> {
            LoadingMain(modifier)
        }
        is MainState.Failure -> {
            ErrorMain(
                error = mainScreenUiState.error,
                modifier = modifier,
                retryAction = { onAction(MainAction.OnRetry) }
            )
        }
        is MainState.Success -> {
            SuccessMain(
                cat = mainScreenUiState.cat,
                onAction = onAction,
            )
        }

        is MainState.ViewProfile ->{
            //displayDetails(mainScreenUiState.cat.id)
        }
    }
}

@Composable
fun LoadingMain(
    modifier: Modifier = Modifier
){
    /*
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading_cat_profile_list),
        modifier = modifier
    )
     */
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieLoading()
    }
}

@Composable
fun ErrorMain(
    error : String,
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
            text = error,
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

        val(catImg,catName) = createRefs()

        // NAME
        Box(
            modifier = Modifier.constrainAs(catName){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart

        ){
            Column {
                CenteredText(
                    text = cat.name,
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(15.dp)
                )
                CenteredText(
                    text = cat.description,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(15.dp)
                )
            }
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

        // BUTTONS
        Row (

        ) {
            Button(
                onClick = { onAction(MainAction.OnLike) },
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(0f)
                    .weight(0.4f)
            ){}

            Spacer(
                modifier = Modifier.weight(0.2f)
            )

            Button(
                onClick = { onAction(MainAction.OnDislike) },
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(0f)
                    .weight(0.4f)
            ){}
        }
    }
}