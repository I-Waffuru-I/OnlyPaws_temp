package com.example.onlypaws.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.repos.ICatRepository
import com.example.onlypaws.repos.MockCatRepo
import kotlinx.coroutines.launch


sealed class ProfileViewUiState {
    data class GotCatprofile(val cat : CatProfile) : ProfileViewUiState()
    data object Loading : ProfileViewUiState()
    data object Error : ProfileViewUiState()
}



class ProfileViewModel (application : Application) : ViewModel() {

    private val catRepo : ICatRepository = MockCatRepo(application)

    var state : ProfileViewUiState by mutableStateOf(ProfileViewUiState.Loading)


    fun getCatProfile(id: Int){
        viewModelScope.launch {

             state = ProfileViewUiState.GotCatprofile(catRepo.getCatProfile(id))
        }
    }
}
