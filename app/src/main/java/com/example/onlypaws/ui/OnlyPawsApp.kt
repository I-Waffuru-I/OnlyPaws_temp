package com.example.onlypaws.ui

import android.app.Application
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
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
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.repos.FireBaseUserRepo
import com.example.onlypaws.repos.IUserAccountRepository
import com.example.onlypaws.ui.screens.AccountScreen
import com.example.onlypaws.ui.screens.CategoriesScreen
import com.example.onlypaws.ui.screens.LogInScreen
import com.example.onlypaws.ui.screens.MainScreen
import com.example.onlypaws.ui.screens.ProfileScreen
import com.example.onlypaws.ui.screens.RegisterDetailsScreen
import com.example.onlypaws.ui.screens.RegisterScreen
import com.example.onlypaws.viewmodels.AccountViewModel
import com.example.onlypaws.viewmodels.LoginViewModel
import com.example.onlypaws.viewmodels.MainScreenViewModel
import com.example.onlypaws.viewmodels.ProfileViewModel
import com.example.onlypaws.viewmodels.RegisterDetailsViewModel
import com.example.onlypaws.viewmodels.RegisterViewModel
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
data class RegisterDetail(
    val user : String,
    val password : String
)

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

    val context = LocalContext.current
    var userIsLoggedIn by remember { mutableStateOf(false)}
    var userId by remember { mutableStateOf("") }

    Scaffold (
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxSize(),
        bottomBar = {
            if (userIsLoggedIn) {

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
        }
    ){
        innerPadding ->

            NavHost(
                navController = navController,
                startDestination = Login,
                modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
            ){

                composable<Login> {
                    val vm : LoginViewModel = viewModel{
                        LoginViewModel()
                    }

                    val onLoggedIn =  {
                        id : String ->

                            userIsLoggedIn = true
                            userId = id
                            navController.navigate(Main) {
                                popUpTo(Main) {
                                    inclusive = true
                                }
                            }
                    }
                    val onRegister = {
                        navController.navigate(Register)
                    }
                    LogInScreen(
                        state = vm.state,
                        onAction = vm::onAction,
                        onRegister = onRegister,
                        onLoggedIn = onLoggedIn,
                    )
                }

                composable<Register> {
                    val vm : RegisterViewModel = viewModel {
                        RegisterViewModel()
                    }

                    val onReturnLogin = {
                        navController.popBackStack()
                    }

                    val onContinueRegister = {
                            email : String, password : String ->
                        navController.navigate(RegisterDetail(email,password))
                    }


                    RegisterScreen(
                        state = vm.state,
                        onAction = vm::onAction,
                        onReturnLogin = { onReturnLogin() },
                        onContinueRegister = onContinueRegister
                    )
                }

                composable<RegisterDetail> {
                    backStackEntry ->
                        val vm : RegisterDetailsViewModel = viewModel {
                            RegisterDetailsViewModel()
                        }

                        val args = backStackEntry.toRoute<RegisterDetail>()
                        vm.state.email = args.user
                        vm.state.password = args.password

                        val onReturnPrevious = {
                            navController.popBackStack()
                        }

                        val onFinishedRegister = {
                                id : String ->

                            userIsLoggedIn = true
                            userId = id
                            navController.navigate(Main) {
                                popUpTo(Main) {
                                    inclusive = true
                                }
                            }
                        }


                        RegisterDetailsScreen(
                            onAction = vm::onAction,
                            state = vm.state,
                            onFinishRegister = onFinishedRegister,
                            onReturnLogin = { onReturnPrevious() }
                        )
                }

                composable<Main> {
                   val vm : MainScreenViewModel = viewModel{
                       MainScreenViewModel(userId)
                   }
                   val clickDisplayDetails = {
                       catId : Int ->
                           navController.navigate(Profile(catId))
                   }

                   MainScreen(
                       onAction = vm::onAction,
                       mainScreenUiState = vm.mainPageState,
                       displayDetails = clickDisplayDetails,
                   )
               }

                composable<Profile> {
                    backStackEntry ->
                    val args = backStackEntry.toRoute<Profile>()
                    val viewModel : ProfileViewModel = viewModel{
                        ProfileViewModel(context.applicationContext as Application)
                    }

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
                    if (userId.isNotBlank()) {

                        val vm: AccountViewModel = viewModel {
                            AccountViewModel(userId)
                        }
                        vm.loadPage(userId)
                        val logOutUser = {

                            userIsLoggedIn = false
                            userId = ""
                            navController.navigate(Login) {
                                popUpTo(Login) {
                                    inclusive = true
                                }
                            }
                        }

                        AccountScreen(
                            onAction = vm::onAction,
                            onLogOutClick = logOutUser,
                            state = vm.state,
                        )
                    }
                }
            }
    }
}
