package com.example.onlypaws.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.USER_INTERESTS
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.account.AccountAction
import com.example.onlypaws.models.account.AccountStateList
import com.example.onlypaws.ui.components.TagsBox

@Composable
fun AccountScreen(
    state : AccountStateList,
    onAction : (AccountAction)->Unit,
    onLogOutClick : ()->Unit,
){

    when(state) {
        is AccountStateList.Failure ->
            AccountFailure(
                error = state.error,
                onRetry = { onAction(AccountAction.OnRetry) }
            )
        AccountStateList.Loading ->
            AccountLoading(
                onRetry = { onAction(AccountAction.OnRetry) }
            )
        is AccountStateList.Success -> {
            AccountSuccess(
                onAction = onAction,
                user = state.user,
            )
        }
        is AccountStateList.LogOut -> {
            onLogOutClick()
        }
    }

}

@Composable
fun AccountLoading(
    onRetry : ()->Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text("Loading \r\n. . .")
        Button(onClick = onRetry) {
            Text("Refresh")
        }
    }
}

@Composable
fun AccountFailure(
    error : String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Text("Failure:")
        Text(error, color = Color.Red)
        Button(onClick = onRetry) {
            Text("Refresh")
        }
    }

}

@Composable
fun AccountSuccess(
    onAction: (AccountAction) -> Unit,
    user : CatProfile,
    modifier: Modifier = Modifier
) {


    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (loginBtn, accountImage, accountNameTxt,boxes) = createRefs()

        Button(
            onClick = { onAction(AccountAction.OnLogOut) },
            modifier = Modifier.constrainAs(loginBtn) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ) {
            Text("Meow out!")
        }

        AsyncImage(
            model = user.image,
            contentDescription = user.name,
            modifier = Modifier.constrainAs(accountImage) {
                top.linkTo(parent.top, 20.dp)
            }
                .height(250.dp)
                .width(250.dp),
            contentScale = ContentScale.Inside

        )

        Text(
            user.name,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.constrainAs(accountNameTxt) {
                top.linkTo(accountImage.bottom,5.dp)
            }
        )

        /*
        Column (
            modifier = Modifier.constrainAs(boxes) {
                top.linkTo(accountNameTxt.bottom,5.dp)
            }
        ){
            USER_INTERESTS.forEach {
                TagsBox(it.value,{},
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp))
            }
        }
         */


    }
}