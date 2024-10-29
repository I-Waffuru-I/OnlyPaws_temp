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

sealed class MainScreenUiState {
    data class GotCatProfiles(val cats : List<CatProfile>) : MainScreenUiState()
    data object Error : MainScreenUiState()
    data object Loading : MainScreenUiState()
}


class MainScreenViewModel(application: Application) : ViewModel() {
    private val catRepo : ICatRepository = MockCatRepo(application)
    var mainPageState : MainScreenUiState by mutableStateOf(MainScreenUiState.Loading)

    init {
        getStarterCats()
    }

    fun getStarterCats() {
        viewModelScope.launch {
           mainPageState = MainScreenUiState.GotCatProfiles(
               catRepo.getCatProfiles()
           )
            //normaal try-catch voor HTTPException / IOException
        }
    }


}