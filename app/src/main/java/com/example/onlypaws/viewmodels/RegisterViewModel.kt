package com.example.onlypaws.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatApiResult
import com.example.onlypaws.models.register.RegisterAction
import com.example.onlypaws.models.register.RegisterState
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RegisterViewModel : ViewModel(){

    var state : RegisterState by mutableStateOf( RegisterState() )


    fun onAction(action : RegisterAction) {
        state = when (action) {
            is RegisterAction.OnImageLinkChange -> {
                if(action.link != "") {
                    state.copy(imageLink = action.link)
                } else {
                    state
                }
            }
            is RegisterAction.OnUsernameChange ->
                state.copy(username = action.username)
            is RegisterAction.OnEmailChange ->
                state.copy(email = action.email)
            is RegisterAction.OnPasswordChange ->
                state.copy(password = action.password)
            RegisterAction.OnImageRandomize ->{

                getRandomCatUrl()
                state
            }
            RegisterAction.OnSignIn ->
                state.copy(triesToReturnLogin = true)
            is RegisterAction.OnRegister ->
                state.copy(triesToRegister = true)
        }
    }

    private fun getRandomCatUrl(){
        viewModelScope.launch {
            val url = "https://api.thecatapi.com/v1/images/search"
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    error("Something failed while fetching a random cat from the API :\r\n ${e.printStackTrace()}")
                }

                override fun onResponse(call: Call, response: Response) {
                    var jsonStr = response.body?.string()

                    jsonStr?.let {
                        try {
                            val filtered = "[]"
                            jsonStr = jsonStr!!.filterNot { filtered.indexOf(it) > -1 }


                            val obj = Json.decodeFromString<CatApiResult>(jsonStr!!)
                            state = state.copy(imageLink = obj.url)
                        } catch (e : SerializationException){
                            e.printStackTrace()
                        }

                    }
                }

            })

        }

    }


}