package com.example.onlypaws.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatApiResult
import com.example.onlypaws.models.register.RegisterAction
import com.example.onlypaws.models.register.RegisterState
import com.example.onlypaws.repos.FireBaseUserRepo
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RegisterViewModel : ViewModel() {
    private val userRepo = FireBaseUserRepo()
    var state: RegisterState by mutableStateOf(RegisterState())


    fun onAction(action: RegisterAction) {
        viewModelScope.launch {

            state = when (action) {

                is RegisterAction.OnEmailChange -> {
                    state.copy(email = action.email)
                }

                is RegisterAction.OnPasswordChange -> {
                    state.copy(password = action.password)
                }

                is RegisterAction.OnRegister -> {
                    if(userRepo.doesUserExist(state.email))
                        state.copy(errorMessage = "Email is already in use!")
                    else
                        state.copy(triesToRegister = true)
                }
            }
            state = state.copy(canSignUp = checkRegisterDetails())
        }
    }

    private fun checkRegisterDetails(): Boolean {
       return state.email.isNotBlank()
               && state.password.isNotBlank()
               && state.password.length > 6
    }
}