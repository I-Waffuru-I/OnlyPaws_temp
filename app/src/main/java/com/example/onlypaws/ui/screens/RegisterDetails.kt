package com.example.onlypaws.ui.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.onlypaws.managers.AccountManager
import com.example.onlypaws.models.register.RegisterAction
import com.example.onlypaws.models.registerDetails.DetailAction
import com.example.onlypaws.models.registerDetails.DetailState
import kotlinx.coroutines.launch

@Composable
fun RegisterDetailsScreen(
    onAction : (DetailAction)->Unit,
    state : DetailState,
    onFinishRegister : (String)->Unit,
    onReturnLogin : ()->Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }

    LaunchedEffect (key1 = state.triesToRegister) {
        if(state.triesToRegister) {
            state.triesToRegister = false
            onFinishRegister(state.email)
        }
    }

    LaunchedEffect (key1 = state.triesToReturnLogin){
        if(state.triesToReturnLogin) {
            state.triesToReturnLogin = false
            onReturnLogin()
        }
    }

    // Launcher om een afbeelding te kiezen
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        println("Got following URI from activity pick : \r $it")
        onAction(DetailAction.OnImageLinkChange(it.toString()))
    }



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(DetailAction.OnBackSignUp)
                },
            ) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, "back")
            }
        },
        floatingActionButtonPosition = FabPosition.Start
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
                value = state.name,
                onValueChange = {
                    onAction(DetailAction.OnChangeName(it))
                },
                label = { Text(text = "Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = state.description,
                onValueChange = {
                    onAction(DetailAction.OnChangeDescription(it))
                },
                label = { Text(text = "Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier.weight(0.4f)
                ) {
                    Text("Select")
                }
                Spacer(Modifier.weight(0.2f))

                Button(
                    onClick = {
                        onAction(DetailAction.OnImageRandomize)
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
                enabled = state.canSignUp,
                onClick = {
                    scope.launch {

                        val result = accountManager.signUp(
                            state
                        )
                        onAction(DetailAction.OnTrySignUp(result))
                    }
                },
            ) {
                Text(text = "Create account!")
            }

        }
    }


}