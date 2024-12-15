package com.example.onlypaws.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.onlypaws.models.register.RegisterAction
import com.example.onlypaws.models.register.RegisterState

@Composable
fun RegisterScreen(
    state : RegisterState,
    onAction: (RegisterAction)->Unit,
    onReturnLogin : ()->Unit,
    onContinueRegister : (String,String)->Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect (key1 = state.triesToReturnLogin){
        if(state.triesToReturnLogin){

            state.triesToReturnLogin = false
            onReturnLogin()
        }
    }

    LaunchedEffect(key1 = state.triesToRegister) {
        if(state.triesToRegister) {
            state.triesToRegister = false
            onContinueRegister(state.email,state.password)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(RegisterAction.OnBackSignIn)
                },
            ) {
                Text (
                    text = "<-",
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Start
    ) { innerPadding ->

        Column (
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            // een afbeelding hier zetten om het nice te maken

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

            state.errorMessage?.let {
                Text(state.errorMessage, color = Color.Red)
            }

            Button(
                enabled = state.canSignUp,
                onClick = {
                    onAction(RegisterAction.OnRegister)
                },
            ) {
                Text(text = "Continue")
            }

        }
    }
}