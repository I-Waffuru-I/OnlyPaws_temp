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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onlypaws.R
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.ui.components.ProfileCard
import com.example.onlypaws.viewmodels.MainScreenUiState


@Composable
fun MainScreen (
    mainScreenUiState: MainScreenUiState,
    retryAction : () -> Unit,
    dislikeClick : ()->Unit,
    likeClick: ()->Unit,
    displayDetails: (Int) -> Unit,
    modifier : Modifier = Modifier,
){

    when(mainScreenUiState) {
        is MainScreenUiState.Loading -> {
            LoadingMain(modifier)
        }
        is MainScreenUiState.Error -> {
            ErrorMain(modifier = modifier, retryAction = retryAction)
        }
        is MainScreenUiState.GotCatProfiles -> {
            SuccessMain(
                like = likeClick,
                dislike = dislikeClick,
                displayDetails = displayDetails,
                cat = mainScreenUiState.cat,
            )
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
    dislike : ()->Unit,
    displayDetails: (Int)->Unit,
    like : ()->Unit,
    modifier:Modifier = Modifier,
){
        ProfileCard(dislike,like, displayDetails ,cat)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview(){
    ErrorMain({})
}