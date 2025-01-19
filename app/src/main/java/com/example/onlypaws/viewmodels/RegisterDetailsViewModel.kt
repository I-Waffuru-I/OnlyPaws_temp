package com.example.onlypaws.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatApiResult
import com.example.onlypaws.models.register.RegisterAction
import com.example.onlypaws.models.register.RegisterState
import com.example.onlypaws.models.register.SignUpResult
import com.example.onlypaws.models.registerDetails.DetailAction
import com.example.onlypaws.models.registerDetails.DetailState
import com.example.onlypaws.repos.FireBaseUserRepo
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import java.io.IOException

class RegisterDetailsViewModel : ViewModel(){

    var state  : DetailState by mutableStateOf( DetailState() )

    private var cachedImageUrl : String = ""

    init {
        getRandomCatUrl()
        state = state.copy(imageLink = "")
    }


    fun onAction(action : DetailAction) {
        viewModelScope.launch {

            state = when (action) {
                is DetailAction.OnImageLinkChange -> {
                    if (action.link != "") {
                        state.copy(imageLink = action.link)
                    } else {
                        state
                    }
                }

                is DetailAction.OnChangeName -> {
                    state.copy(name = action.name)
                }
                DetailAction.OnImageRandomize -> {
                    getRandomCatUrl()
                    state.copy(imageLink = cachedImageUrl)
                }

                DetailAction.OnBackSignUp ->
                    state.copy(triesToReturnLogin = true)

                is DetailAction.OnTrySignUp -> {
                    when(action.result){
                        SignUpResult.Cancelled ->
                            state.copy()
                        is SignUpResult.Failure ->
                            state.copy()
                        is SignUpResult.Success ->
                            state.copy(triesToRegister = true)
                    }
                }

                is DetailAction.OnChangeDescription -> {
                    state.copy(description = action.description)
                }
            }
        }

        state = state.copy(canSignUp = checkRegisterDetails())
    }

    private fun checkRegisterDetails() : Boolean {
        return state.name.isNotBlank()
        && state.description.isNotBlank()
        && state.imageLink.isNotBlank()
    }

    private fun getRandomCatUrl() {
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
                        Log.i("Random Cat","Got URL: ${obj.url}")
                        if(obj.url.isNotBlank()){
                            cachedImageUrl = obj.url
                        }
                    } catch (e : SerializationException){
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}