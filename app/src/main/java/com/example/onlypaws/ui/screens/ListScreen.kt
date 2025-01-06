package com.example.onlypaws.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.onlypaws.R
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.list.ListAction
import com.example.onlypaws.models.list.ListState
import com.example.onlypaws.ui.components.ListItemDisplay
import com.example.onlypaws.ui.components.CenteredText

@Composable
fun ListScreen(
    cats : List<CatProfile>,
    state : ListState,
    onAction : (ListAction)->Unit,
    modifier : Modifier = Modifier
) {
    when (state) {
        is ListState.Failure -> FailureList(
            error = state.error,
            onRetry = { onAction(ListAction.OnRefresh) },
            modifier = modifier
        )
        ListState.Loading -> LoadingList(
            onRetry = { onAction(ListAction.OnRefresh) },
            modifier = modifier
        )
        is ListState.SuccessLike -> SuccessList(
            cats = cats,
            liked = true,
            onAction = onAction,
            modifier = modifier
        )

        is ListState.SuccessDislike ->  SuccessList(
            cats = cats,
            liked = false,
            onAction = onAction,
            modifier = modifier
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessList(
    cats : List<CatProfile>,
    liked : Boolean,
    onAction: (ListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDialogShown = remember { mutableStateOf(false)}
    val isImageShown = remember { mutableStateOf(false) }
    val currentCatId = remember { mutableIntStateOf(-1) }
    val currentCat = remember { mutableStateOf(CatProfile()) }


    // CONFIRM DIALOG
    if(isDialogShown.value){
        BasicAlertDialog(
            onDismissRequest = {
            isDialogShown.value = false
            }
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                val t = if(liked) R.string.list_check_delete else R.string.list_check_add
                CenteredText(
                    text = stringResource(R.string.general_confirmation),
                    fontSize = 30.sp
                )
                CenteredText(
                    text = stringResource(t),
                    fontSize = 15.sp
                )
                Button(
                    onClick = {
                        isDialogShown.value = false
                        onAction(ListAction.OnToggleLiked(currentCatId.intValue))
                    }
                ){
                    Text(
                        text = stringResource(R.string.general_confirm)
                    )
                }
            }
        }
    }

    // IMAGE DIALOG
    if(isImageShown.value){
        BasicAlertDialog(
            onDismissRequest = {
                isImageShown.value = false
            },
        ) {
            AsyncImage(
                model = currentCat.value.image,
                contentDescription = currentCat.value.name,
                modifier = Modifier.fillMaxSize()
            )
        }
    }


    // SCREEN ITSELF
    Column (
        modifier = modifier
            .fillMaxSize()
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceDim)
        ){
            val t = if(liked) R.string.list_title_like else R.string.list_title_dislike
            val i = if(liked) Icons.Rounded.FavoriteBorder else Icons.Rounded.Favorite
            CenteredText(
                text = stringResource(t),
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(15.dp)
            )

            IconButton(
                onClick = {
                    onAction(ListAction.OnSwitchDisplayed)
                }
            ) {
                Icon(
                    imageVector = i,
                    contentDescription = stringResource(R.string.general_swap),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        LazyColumn {
            items(cats)  {
                ListItemDisplay(
                    title = it.name,
                    image = it.image,
                    description = it.description,
                    isAdded = liked,
                    onToggle = {
                        isDialogShown.value = true
                        currentCatId.intValue = it.id
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clickable {
                            currentCat.value = it
                            isImageShown.value = true
                        }
                )
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }
        }
    }
}

@Composable
private fun LoadingList(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        CenteredText(
            text = stringResource(R.string.loading_cat_profile_list)
        )
        Button(
            onClick = onRetry
        ) {
            Text(stringResource(R.string.refresh_cat_profile_list))
        }
    }
}

@Composable
private fun FailureList(
    error : String,
    onRetry : ()->Unit,
    modifier: Modifier = Modifier
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        CenteredText(
            text = error,
            color = MaterialTheme.colorScheme.error
        )
        Button(
            onClick = onRetry
        ) {
            Text(stringResource(R.string.refresh_cat_profile_list))
        }
    }
}
