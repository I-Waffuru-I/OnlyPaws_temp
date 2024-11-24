package com.example.onlypaws.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.onlypaws.managers.AccountManager
import com.example.onlypaws.models.login.LoginAction
import com.example.onlypaws.models.login.LoginState
import kotlinx.coroutines.launch


@Composable
fun LogInScreen(
    state : LoginState,
    onAction : (LoginAction) -> Unit,
    onLoggedIn : (String)->Unit,
){
    // AccManager hier maken
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
    LaunchedEffect(key1 = state.loggedInUser) {
        println("Launched effect with username: "+state.loggedInUser)
        if(state.loggedInUser != null) {
            onLoggedIn(state.loggedInUser)
        }
    }


    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)

    ) {
        TextField(
            value = state.username,
            onValueChange = {
                onAction(LoginAction.OnUsernameChange(it))
            },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = state.password,
            onValueChange = {
                onAction(LoginAction.OnPasswordChange(it))
            },
            label = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Row (
            Modifier.fillMaxWidth()
        ) {
            Text("Register")
            Spacer(Modifier.width(8.dp))
            Switch(
                checked = state.isRegister,
                onCheckedChange = {
                    onAction(LoginAction.OnToggleIsRegister)
                }
            )
        }

        if(state.errorMessage != null) {
            Text(
                text = state.errorMessage.toString(),
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {
                scope.launch {
                    if(state.isRegister) {

                        val result = accountManager.signUp(
                            username = state.username,
                            password = state.password,
                        )
                        onAction(LoginAction.OnSignUp(result))
                    } else {
                        val result = accountManager.signIn()
                        onAction(LoginAction.OnSignIn(result))
                    }
                }
            },
        ){
            Text(text = if(state.isRegister) "Register" else "Login")
        }
    }
}

