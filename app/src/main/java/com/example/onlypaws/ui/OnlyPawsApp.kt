package com.example.onlypaws.ui

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onlypaws.ui.screens.MainScreen
import com.example.onlypaws.viewmodels.MainScreenViewModel
import kotlinx.serialization.Serializable

@Serializable
object Main

@Composable
fun OnlyPawsApp(

    navController : NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,

){

    Scaffold (
        modifier = modifier,
    ){
        innerPadding ->

            NavHost(
                navController = navController,
                startDestination = Main,
                modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
            ){

               composable<Main> {
                   val context = LocalContext.current
                   val viewModel : MainScreenViewModel = viewModel() {
                      MainScreenViewModel(context.applicationContext as Application)
                   }


                    MainScreen(
                        retryAction = { viewModel.getStarterCats() },
                        mainScreenUiState = viewModel.mainPageState,
                        modifier =  Modifier.fillMaxSize().padding(innerPadding)
                    )
               }





            }
    }
}