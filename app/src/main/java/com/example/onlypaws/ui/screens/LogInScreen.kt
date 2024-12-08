package com.example.onlypaws.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.registerForAllProfilingResults
import androidx.lifecycle.ViewModel
import com.example.onlypaws.managers.AccountManager
import com.example.onlypaws.models.login.LoginAction
import com.example.onlypaws.models.login.LoginState
import com.example.onlypaws.ui.theme.OnlyPawsTheme
import com.example.onlypaws.viewmodels.LoginViewModel
import kotlinx.coroutines.launch


@Composable
fun LogInScreen(
    state : LoginState,
    onAction : (LoginAction) -> Unit,
    onLoggedIn : (String)->Unit,
    onRegister : ()->Unit,
){
    // AccManager hier maken zo die lifetime niet buiten de screen gaat
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }

    // Open credentials menu om in te loggen eens de pagina open gaat
    LaunchedEffect(key1 = true) {
        val result = accountManager.signIn()
        onAction(LoginAction.OnSignIn(result))
    }

    // Als username niet null is, navigeer verden naar de app
    LaunchedEffect(key1 = state.triesToLogIn) {
        if(state.loggedInUser != null && state.triesToLogIn) {
            state.triesToLogIn = false
            onLoggedIn(state.loggedInUser)
        }
    }

    // Als ge op Register klikt en er gebeurt niks fout komt ge hier uit
    LaunchedEffect(key1 = state.triesToRegister) {
        if(state.triesToRegister)
            onRegister()
    }

    Column(
        modifier = Modifier.padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        if(state.errorMessage != null) {
            Text(
                text = state.errorMessage.toString(),
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {
                scope.launch {
                    onAction(LoginAction.OnSignUp)
                }
            },
        ){
            Text(text = "Register")
        }


        Button(
            onClick = {
                scope.launch {
                    val result = accountManager.signIn()
                    onAction(LoginAction.OnSignIn(result))
                }
            },
        ){
            Text(text = "Login")
        }
    }
}