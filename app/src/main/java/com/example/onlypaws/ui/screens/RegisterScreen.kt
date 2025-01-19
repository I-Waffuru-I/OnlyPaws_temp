package com.example.onlypaws.ui.screens

import android.view.ViewTreeObserver
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onlypaws.R
import com.example.onlypaws.models.register.RegisterAction
import com.example.onlypaws.models.register.RegisterState
import com.example.onlypaws.ui.components.CenteredText
import com.example.onlypaws.ui.components.CenteredTextField
import com.example.onlypaws.ui.components.PasswordField

@Composable
fun RegisterScreen(
    state : RegisterState,
    onAction: (RegisterAction)->Unit,
    onReturnLogin : ()->Unit,
    onContinueRegister : (String,String)->Unit,
    modifier: Modifier = Modifier
) {

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
                    onReturnLogin()
                },
            ) {
                Icon(
                   imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back to Login!"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Start
    ) { innerPadding ->

        var offset : IntOffset by remember { mutableStateOf(IntOffset(0,-40)) }
        val view = LocalView.current
        val viewTreeObserver = view.viewTreeObserver
        DisposableEffect(viewTreeObserver) {
            val listener = ViewTreeObserver.OnGlobalLayoutListener {
                val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                    ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
                offset = if(isKeyboardOpen) offset.copy(y = -240) else offset.copy(y = -40)
            }

            viewTreeObserver.addOnGlobalLayoutListener(listener)
            onDispose {
                viewTreeObserver.removeOnGlobalLayoutListener(listener)
            }
        }

        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding).padding(horizontal = 10.dp)
                .offset {
                    offset
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.empty_profile_icon),
                contentDescription = "cat image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp),
            )

            Spacer(modifier = Modifier.height(15.dp))

            CenteredTextField(
                text = state.email,
                onValueChange = {
                    onAction(RegisterAction.OnEmailChange(it))
                },
                label = {
                    Text(
                        text = stringResource(R.string.register_label_email),
                        color = MaterialTheme.colorScheme.secondary
                    )},
                backgroundColor = MaterialTheme.colorScheme.onSecondary,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

            PasswordField(
                password = state.password,
                onValueChange = {
                    onAction(RegisterAction.OnPasswordChange(it))
                },
                backgroundColor = MaterialTheme.colorScheme.onSecondary,
                color = MaterialTheme.colorScheme.secondary,
                label = {
                    Text(
                        text = stringResource(R.string.register_label_password),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

            state.errorMessage?.let {
                CenteredText(state.errorMessage, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(5.dp))
            }

            Button(
                enabled = state.canSignUp,
                onClick = {
                    onAction(RegisterAction.OnRegister)
                },
            ) {
                Text( stringResource(R.string.register_btn_continue) )
            }

        }
    }
}