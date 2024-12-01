package com.example.onlypaws.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.repos.ICatRepository
import com.example.onlypaws.repos.MockCatRepo
import kotlinx.coroutines.launch

sealed class MainScreenUiState {
    data class Success(val cat : CatProfile) : MainScreenUiState()
    data class Failure(val error : String) : MainScreenUiState()
    data object Loading : MainScreenUiState()
}


class MainScreenViewModel(application: Application) : ViewModel() {
    private val catRepo : ICatRepository = MockCatRepo(application)

    var mainPageState : MainScreenUiState by mutableStateOf(MainScreenUiState.Loading)

    init {
        getStarterCats()
    }

    fun getStarterCats() {
        getNextCat()
    }

    fun getNextCat ()  {
         viewModelScope.launch {

             mainPageState = when (val rslt = catRepo.getCatProfile(0)) {
                 is GetDbResult.Failure -> {
                     MainScreenUiState.Failure(rslt.error)
                 }

                 is GetDbResult.Success -> {
                     if (rslt.value is CatProfile){
                         MainScreenUiState.Success(rslt.value)
                     } else {
                         MainScreenUiState.Failure("Got something from the repo, but it's not a user profile...")
                     }
                 }
             }
        }
    }
}