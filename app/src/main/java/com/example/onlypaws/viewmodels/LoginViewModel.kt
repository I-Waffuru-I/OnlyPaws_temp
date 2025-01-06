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
                        state.copy(hasNoCredentials = true)
                    }
                }
            }
        }
    }

}