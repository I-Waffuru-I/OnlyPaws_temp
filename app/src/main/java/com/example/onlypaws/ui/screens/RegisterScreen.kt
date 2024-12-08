package com.example.onlypaws.ui.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.onlypaws.managers.AccountManager
import com.example.onlypaws.models.register.RegisterAction
import com.example.onlypaws.models.register.RegisterState
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    state : RegisterState,
    onAction: (RegisterAction)->Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }

    // Launcher om een afbeelding the kiezen
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        println("Got following URI from activity pick : \r $it")
        onAction(RegisterAction.OnImageLinkChange(it.toString()))
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(RegisterAction.OnSignIn)
                },
            ) {
                Text (
                    text = "<-",
                )
            }
        }
    ) { innerPadding ->

        Column (
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            if (state.imageLink != "") {

                AsyncImage(
                    model = state.imageLink,
                    contentDescription = "cat image",
                    modifier = Modifier.width(150.dp),
                )
            }

            TextField(
                value = state.username,
                onValueChange = {
                    onAction(RegisterAction.OnUsernameChange(it))
                },
                label = { Text(text = "Username") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = state.email,
                onValueChange = {
                    onAction(RegisterAction.OnEmailChange(it))
                },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = state.password,
                onValueChange = {
                    onAction(RegisterAction.OnPasswordChange(it))
                },
                label = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    modifier = Modifier.weight(0.4f)
                ) {
                    Text("Select")
                }
                Spacer(Modifier.weight(0.2f))

                Button(
                    onClick = {
                        onAction(RegisterAction.OnImageRandomize)
                    },
                    modifier = Modifier.weight(0.4f)
                ) {
                    Text("Random")
                }

            }
            Row(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "",
                    modifier = Modifier.weight(0.7f)
                )
            }


            Button(
                onClick = {
                    scope.launch {

                        val rslt = accountManager.signUp(
                            state
                        )
                        onAction(RegisterAction.OnRegister(rslt))
                    }
                },
            ) {
                Text(text = "Create account!")
            }

        }
    }
}