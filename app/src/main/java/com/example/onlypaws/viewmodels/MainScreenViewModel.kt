package com.example.onlypaws.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.CatProfileDuo
import com.example.onlypaws.repos.ICatRepository
import com.example.onlypaws.repos.MockCatRepo
import com.example.onlypaws.ui.screens.MainScreenPreview
import kotlinx.coroutines.launch

sealed class MainScreenUiState {
    data class GotCatProfiles(val cats : CatProfileDuo) : MainScreenUiState()
    data object Error : MainScreenUiState()
    data object Loading : MainScreenUiState()
}


class MainScreenViewModel(application: Application) : ViewModel() {
    private val catRepo : ICatRepository = MockCatRepo(application)
    private var rememberedCat : CatProfile by mutableStateOf(CatProfile(-1,"","",""))

    var mainPageState : MainScreenUiState by mutableStateOf(MainScreenUiState.Loading)

    init {
        getStarterCats()
    }

    fun getStarterCats() {
        viewModelScope.launch {
            val c = catRepo.getSuggestedCatProfile()
            mainPageState = MainScreenUiState.GotCatProfiles(
                CatProfileDuo(
                   firstCat = catRepo.getLastCatProfile(),
                   lastCat = c
               )
            )

            rememberedCat = c

            //normaal try-catch voor HTTPException / IOException
        }
    }

    fun getNextCat ()  {

         viewModelScope.launch {
            val c = catRepo.getSuggestedCatProfile()
            mainPageState = MainScreenUiState.GotCatProfiles (
                CatProfileDuo(
                    firstCat = rememberedCat,
                    lastCat = c,
                )
            )
            rememberedCat = c
             // hier ook checken voor HTTPException / IO Exception
        }
    }
}