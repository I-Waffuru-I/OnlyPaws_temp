package com.example.onlypaws.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.profile.ProfileState
import com.example.onlypaws.repos.FirebaseCatRepo
import com.example.onlypaws.repos.ICatRepository
import com.example.onlypaws.repos.MockCatRepo
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val catRepo : ICatRepository = FirebaseCatRepo()

    var state : ProfileState by mutableStateOf(ProfileState.Loading)


    fun getCatProfile(id: Int){
        viewModelScope.launch {
            state = when (val result = catRepo.getCatProfileFromID(id)) {
                is GetDbResult.Failure -> {
                    ProfileState.Failure(result.error)
                }

                is GetDbResult.Success -> {
                    if(result.value is CatProfile){
                        ProfileState.Success(result.value)
                    } else {
                        ProfileState.Failure("Got something from the repo, but it isn't a Cat Profile!")
                    }
                }
            }
        }
    }
}
