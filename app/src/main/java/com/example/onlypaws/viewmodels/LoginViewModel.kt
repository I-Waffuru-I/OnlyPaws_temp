package com.example.onlypaws.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.login.LoginAction
import com.example.onlypaws.models.login.LoginState
import com.example.onlypaws.models.login.SignInResult
import com.example.onlypaws.repos.FireBaseUserRepo
import com.example.onlypaws.repos.IUserAccountRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val firebaseUserRepo : IUserAccountRepository = FireBaseUserRepo()
    var state :LoginState by mutableStateOf(LoginState())



    fun onAction(action : LoginAction){
        when (action) {
            is LoginAction.OnSignUp -> {
                state = state.copy(triesToRegister = true)
            }
            is LoginAction.OnSignIn -> {
                state = when (action.result) {
                    SignInResult.Cancelled -> {
                        val m = "Sign up was cancelled"
                        println("LOGIN CANCEL: $m")
                        state.copy(errorMessage = m)
                    }

                    is SignInResult.Failure -> {
                        val m = action.result.error
                        println("LOGIN FAILURE: $m")
                        state.copy(errorMessage = m)
                    }

                    is SignInResult.Success -> {
                        val m = action.result.username
                        println("LOGIN INFO: $m")
                        state.copy(loggedInUser = m, triesToLogIn = true)
                    }

                    SignInResult.NoCredentials -> {

                        val m = "No credentials"
                        println("LOGIN FAILURE: $m")
                        state.copy(errorMessage = m)
                    }
                }
            }
        }
    }

    fun getUserInfo(username : String) : UserProfile? {
        if (username == ""){
            println("LOGIN FAILURE : Provide a username!")
            return null
        }

        var user : UserProfile? = null
        viewModelScope.launch {
            when (val rslt = firebaseUserRepo.getLoggedInUser(username)) {
                is GetDbResult.Failure -> {
                    println("LOGIN FAILURE: ${rslt.error}")
                }
                is GetDbResult.Success -> {
                    when (rslt.value) {
                        is UserProfile -> {
                            user = rslt.value as UserProfile
                            println("LOGIN INFO : Parsed to user '${user!!.accountName}'")
                        }
                        else -> {
                            println("LOGIN FAILURE : Couldn't parse data into a user")
                        }
                    }
                }
            }
        }
        return user
    }
}