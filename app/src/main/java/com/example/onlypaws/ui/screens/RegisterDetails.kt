package com.example.onlypaws.ui.screens

import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.compose.AsyncImage
import com.example.onlypaws.R
import com.example.onlypaws.managers.AccountManager
import com.example.onlypaws.models.registerDetails.DetailAction
import com.example.onlypaws.models.registerDetails.DetailState
import com.example.onlypaws.ui.components.CenteredTextField
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
        if(it != null) {
            onAction(DetailAction.OnImageLinkChange(it.toString()))
            Log.i("Media Picker","URI is not null!")
        }
        Log.i("Media Picker","Got following URI from activity pick : \r ${it.toString()}")
    }



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(DetailAction.OnBackSignUp)
                },
            ) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.general_return))
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
                .offset{
                    offset
                }
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {


            if (state.imageLink.isBlank()) {
                Image(
                    painter = painterResource(R.drawable.empty_profile_icon),
                    contentDescription = "cat image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp),
                )

            }else {
                AsyncImage(
                    model = state.imageLink,
                    contentDescription = "cat image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            CenteredTextField(
                text = state.name,
                onValueChange = {
                    onAction(DetailAction.OnChangeName(it))
                },
                label = {
                    Text(
                        text = stringResource(R.string.register_label_name),
                        color = MaterialTheme.colorScheme.secondary
                    )},
                backgroundColor = MaterialTheme.colorScheme.onSecondary,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

            CenteredTextField(
                text = state.description,
                onValueChange = {
                    onAction(DetailAction.OnChangeDescription(it))
                },
                label = {
                    Text(
                        text = stringResource(R.string.register_label_description),
                        color = MaterialTheme.colorScheme.secondary
                    )},
                backgroundColor = MaterialTheme.colorScheme.onSecondary,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

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
                    Text( stringResource(R.string.register_btn_manual) )
                }
                Spacer(Modifier.weight(0.2f))

                Button(
                    onClick = {
                        onAction(DetailAction.OnImageRandomize)
                    },
                    modifier = Modifier.weight(0.4f)
                ) {
                    Text( stringResource(R.string.register_btn_random) )
                }

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
                Text( stringResource(R.string.register_btn_register) )
            }
        }
    }


}