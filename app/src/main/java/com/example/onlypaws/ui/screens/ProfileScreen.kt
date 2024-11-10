package com.example.onlypaws.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.onlypaws.R
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.viewmodels.ProfileViewUiState

@Composable
fun ProfileScreen(
    goBackEvent : ()->Unit,
    retryAction: () -> Unit,
    state : ProfileViewUiState,
    modifier: Modifier = Modifier,
){

    when (state) {

        is ProfileViewUiState.Loading -> {
            LoadingProfile(modifier = modifier)
        }

        is ProfileViewUiState.Error -> {
            ErrorProfile(
                retryAction = retryAction,
                modifier = modifier,
            )
        }
        is ProfileViewUiState.GotCatprofile -> {
            SuccessProfile(
                goBackEvent = goBackEvent,
                state.cat,
                modifier = modifier,
            )
        }

    }

}


@Composable
fun SuccessProfile(
    goBackEvent: () -> Unit,
    cat: CatProfile,
    modifier : Modifier = Modifier
)
{

    Column (

        modifier = modifier
            .verticalScroll(rememberScrollState())

    ){

        SmallFloatingActionButton(
            onClick = goBackEvent,
            modifier = Modifier.size(30.dp)
                .align(Alignment.End)
        ) {
            Text(text="<")
        }
        // image ?
        Box(
            modifier = Modifier
        ){
            Image(
                painter = rememberAsyncImagePainter(cat.image),
                contentDescription = "Cat image",
                modifier = Modifier.size(200.dp)
            )
        }

        // name + age? + description
        Card (
            modifier = Modifier
        ){
            Text(text = cat.name)
            Text(text = cat.description)
        }


        // tags
        Box(
            modifier = Modifier
        ){

        }

        // temp
        Box(
            modifier = Modifier
        ){

        }
    }
}


@Composable
fun ErrorProfile(
    retryAction : ()->Unit,
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
fun LoadingProfile(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading_cat_profile_list),
        modifier = modifier
    )
}
