package com.example.onlypaws.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.onlypaws.R
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.CatProfileDuo
import com.example.onlypaws.ui.components.ProfileCard
import com.example.onlypaws.viewmodels.MainScreenUiState


@Composable
fun MainScreen (
    mainScreenUiState: MainScreenUiState,
    retryAction : () -> Unit,
    modifier : Modifier = Modifier,
    dislikeClick : ()->Unit,
    likeClick: ()->Unit,
){

    when(mainScreenUiState) {
        is MainScreenUiState.Loading -> {
            LoadingScreen(modifier)
        }
        is MainScreenUiState.Error -> {
            ErrorScreen(modifier = modifier, retryAction = retryAction)
        }
        is MainScreenUiState.GotCatProfiles -> {
            SuccessScreen(
                like = likeClick,
                dislike = dislikeClick,
                cats = mainScreenUiState.cats,
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading_cat_profile_list),
        modifier = modifier
    )
}
@Composable
fun ErrorScreen(
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
fun SuccessScreen(
    cats : CatProfileDuo,
    dislike : ()->Unit,
    like : ()->Unit,
    modifier:Modifier = Modifier,
){


        ProfileCard(dislike,like, cats.lastCat)
        ProfileCard(dislike,like, cats.firstCat)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview(){
    ErrorScreen({})
}