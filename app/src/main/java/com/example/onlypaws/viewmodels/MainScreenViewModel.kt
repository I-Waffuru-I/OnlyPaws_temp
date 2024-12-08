package com.example.onlypaws.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.main.MainAction
import com.example.onlypaws.models.main.MainState
import com.example.onlypaws.models.main.MainStateList
import com.example.onlypaws.repos.FireBaseUserRepo
import com.example.onlypaws.repos.FirebaseCatRepo
import com.example.onlypaws.repos.ICatRepository
import com.example.onlypaws.repos.IUserAccountRepository
import kotlinx.coroutines.launch



class MainScreenViewModel(userId : String) : ViewModel() {
    private val catRepo : ICatRepository = FirebaseCatRepo()
    private val userRepo : IUserAccountRepository = FireBaseUserRepo()
    private var currentCatId = -1 // the id the last seen cat, stored in the user account
    private var userCatId = -1 // the catId of the logged in user

    var mainPageState : MainState by mutableStateOf(MainState())

    init {
        getUserInfo(userId)
        getCurrentCat()
    }

// Handler voor de screen interacties
    fun onAction(action : MainAction) {
        when(action)  {
            MainAction.OnDislike -> getNextCat()
            MainAction.OnLike -> getNextCat()
            MainAction.OnProfileView ->
                mainPageState = mainPageState.copy(view = true)
            MainAction.OnRetry -> getCurrentCat()
        }
    }

    private fun getCurrentCat() {

        viewModelScope.launch {
            currentCatId += if(currentCatId == userCatId)
                 1
            else 0
            mainPageState.state = when (val result = catRepo.getCatProfile(currentCatId)) {
                is GetDbResult.Failure -> {
                    MainStateList.Failure(result.error)
                }

                is GetDbResult.Success -> {
                    if (result.value is CatProfile){
                        mainPageState.cat = result.value
                        MainStateList.Success
                    } else {
                        MainStateList.Failure("Got something from the repo, but it's not a user profile...")
                    }
                }
            }
        }
    }

    private fun getNextCat ()  {
         viewModelScope.launch {

             currentCatId += if (currentCatId+1 == userCatId)
                  2
             else 1

             mainPageState.state = when (val result = catRepo.getCatProfile(currentCatId)) {
                 is GetDbResult.Failure -> {
                     MainStateList.Failure(result.error)
                 }

                 is GetDbResult.Success -> {
                     if (result.value is CatProfile){
                         mainPageState.cat = result.value
                         MainStateList.Success
                     } else {
                         MainStateList.Failure("Got something from the repo, but it's not a user profile...")
                     }
                 }
             }
        }
    }

    private fun getUserInfo(userId: String){

        viewModelScope.launch {
            when(val u = userRepo.getLoggedInUser(userId)){
                is GetDbResult.Failure -> {
                    mainPageState.state = MainStateList.Failure(error = u.error)
                }
                is GetDbResult.Success -> {
                    if(u.value is UserProfile){
                        userCatId = u.value.catId
                        currentCatId = u.value.currentViewedId
                    } else {
                        mainPageState.state = MainStateList.Failure(
                            "VM : User gotten from repo isn't correctly parsed!"
                        )
                    }
                }
            }
        }
    }


}