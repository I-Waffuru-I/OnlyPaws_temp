package com.example.onlypaws.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.onlypaws.R
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.account.AccountAction
import com.example.onlypaws.models.account.AccountStateList
import com.example.onlypaws.ui.components.ValueUpdateField

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


    val context = LocalContext.current
    val saveText = stringResource(R.string.account_btn_save)


    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (logOutBtn, accountImage, accountName, accDescription) = createRefs()

        Box(
            modifier = Modifier.constrainAs(logOutBtn) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            },
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { onAction(AccountAction.OnLogOut) },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                    contentDescription = stringResource(R.string.account_btn_logout)
                )
            }
        }


        AsyncImage(
            model = user.image,
            contentDescription = user.name,
            modifier = Modifier
                .constrainAs(accountImage) {
                    top.linkTo(logOutBtn.bottom, 10.dp)
                    start.linkTo(parent.start,40.dp)
                    end.linkTo(parent.end,40.dp)
                    width = Dimension.fillToConstraints
                },
            contentScale = ContentScale.Inside
        )

        ValueUpdateField(
            onEditPress = {
                onAction(AccountAction.OnSaveUsername(it))
                Toast.makeText(context,saveText,Toast.LENGTH_SHORT).show()
            },
            title = stringResource(R.string.account_field_username),
            value = user.name,
            modifier = Modifier
                .constrainAs(accountName) {
                    top.linkTo(accountImage.bottom, 10.dp)
                }
        )

        ValueUpdateField(
            onEditPress = {
                onAction(AccountAction.OnSaveDescription(it))
                Toast.makeText(context,saveText,Toast.LENGTH_SHORT).show()
            },
            title = stringResource(R.string.account_field_description),
            value = user.description,
            modifier = Modifier
                .constrainAs(accDescription) {
                    top.linkTo(accountName.bottom, 5.dp)
                }
        )
    }
}