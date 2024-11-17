package com.example.onlypaws.ui

import android.app.Application
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.onlypaws.R
import com.example.onlypaws.ui.components.NavBottomBar
import com.example.onlypaws.ui.screens.AccountScreen
import com.example.onlypaws.ui.screens.CategoriesScreen
import com.example.onlypaws.ui.screens.MainScreen
import com.example.onlypaws.ui.screens.ProfileScreen
import com.example.onlypaws.viewmodels.MainScreenViewModel
import com.example.onlypaws.viewmodels.ProfileViewModel
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
data class Profile(val cat : Int)

@Serializable
object Categories

@Serializable
object Account

data class Route <T : Any>(
    val name : String,
    val route : T,
    val icon : ImageVector,
)


@Composable
fun OnlyPawsApp(

    modifier: Modifier = Modifier,
    navController : NavHostController = rememberNavController(),
    contentWindowInsets : WindowInsets = WindowInsets(0.dp,0.dp,0.dp,0.dp),

){

    val routeList = listOf(
        Route(
            "Main",
            Main,
            Icons.Rounded.Home
        ),
        Route(
            "Categories",
            Categories,
            Icons.Rounded.MoreVert
        ),
        Route(
            "Account",
            Account,
            Icons.Rounded.AccountCircle
        )
    )

    Scaffold (
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.surfaceBright,
            ){
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                routeList.forEach {
                    route ->

                    val selected = currentDestination?.hierarchy?.any {
                        it.hasRoute(route.route::class)
                    } == true

                    val c : Color = when {
                        selected -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.surfaceDim
                    }

                    BottomNavigationItem(
                        icon = {
                            Icon(route.icon,route.name, tint = c)
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(route.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
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

                composable<Categories> {
                    CategoriesScreen()
                }

                composable<Account> {
                    AccountScreen()
                }
            }
    }
}
