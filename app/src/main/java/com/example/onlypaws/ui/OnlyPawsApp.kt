package com.example.onlypaws.ui

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.onlypaws.R
import com.example.onlypaws.ui.screens.MainScreen
import com.example.onlypaws.ui.screens.ProfileScreen
import com.example.onlypaws.viewmodels.MainScreenViewModel
import com.example.onlypaws.viewmodels.ProfileViewModel
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
data class Profile(val cat : Int)


@Composable
fun OnlyPawsApp(

    navController : NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,

){

    Scaffold (
        modifier = modifier
            .background(color = colorResource(R.color.main_background_1))
            .fillMaxSize(),
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
                   val viewModel : MainScreenViewModel = viewModel{
                       MainScreenViewModel(context.applicationContext as Application)
                   }
                   val clickDisplayDetails = {
                       catId : Int ->
                           navController.navigate(Profile(catId))
                   }

                   MainScreen(
                       retryAction = { viewModel.getStarterCats() },
                       mainScreenUiState = viewModel.mainPageState,
                       dislikeClick = { viewModel.getNextCat() },
                       likeClick = { viewModel.getNextCat()},
                       displayDetails = clickDisplayDetails,
                       modifier =  Modifier.fillMaxSize().padding(innerPadding),
                   )
               }


                composable<Profile> {
                    backStackEntry ->
                    val context = LocalContext.current
                    val args = backStackEntry.toRoute<Profile>()
                    val viewModel : ProfileViewModel = viewModel{
                        ProfileViewModel(context.applicationContext as Application)
                    }

                    viewModel.getCatProfile(args.cat)

                    ProfileScreen(
                        goBackEvent = { navController.popBackStack() },
                        state = viewModel.state,
                        retryAction = { viewModel.getCatProfile(args.cat) }
                    )

                }
            }
    }
}