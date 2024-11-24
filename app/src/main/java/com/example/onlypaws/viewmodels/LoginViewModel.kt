package com.example.onlypaws.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.onlypaws.models.login.LoginAction
import com.example.onlypaws.models.login.LoginState
import com.example.onlypaws.models.login.SignInResult
import com.example.onlypaws.models.login.SignUpResult

class LoginViewModel : ViewModel() {
    var state :LoginState by mutableStateOf(LoginState())


    fun onAction(action : LoginAction){
        when (action) {
            is LoginAction.OnPasswordChange -> {
                state = state.copy(password = action.password)
            }
            is LoginAction.OnSignUp -> {
                state = when (action.result) {
                    SignUpResult.Cancelled -> {
                        state.copy(errorMessage = "Sign up was cancelled")
                    }

                    is SignUpResult.Failure -> {
                        state.copy(username = "Sign failed" + action.result.error)
                    }

                    is SignUpResult.Success -> {
                        state.copy(username = action.result.username)
                    }
                }
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
                        state.copy(username = m)
                    }

                    SignInResult.NoCredentials -> {

                        val m = "No credentials"
                        println("LOGIN FAILURE: $m")
                        state.copy(errorMessage = m)
                    }
                }
            }
            LoginAction.OnToggleIsRegister -> {
                state = state.copy(isRegister = !state.isRegister)
            }
            is LoginAction.OnUsernameChange ->{
                state = state.copy(username = action.username)
            }
        }
    }
}