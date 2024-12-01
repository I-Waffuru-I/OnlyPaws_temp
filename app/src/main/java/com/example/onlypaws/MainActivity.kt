package com.example.onlypaws

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.onlypaws.ui.theme.OnlyPawsTheme
import com.example.onlypaws.ui.OnlyPawsApp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            OnlyPawsTheme {
                OnlyPawsApp(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
