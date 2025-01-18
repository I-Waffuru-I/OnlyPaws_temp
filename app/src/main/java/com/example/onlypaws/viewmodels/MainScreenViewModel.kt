package com.example.onlypaws.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.R
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult
import com.example.onlypaws.models.main.MainAction
import com.example.onlypaws.models.main.MainState
import com.example.onlypaws.repos.FireBaseUserRepo
import com.example.onlypaws.repos.FirebaseCatRepo
import com.example.onlypaws.repos.ICatRepository
import com.example.onlypaws.repos.IUserAccountRepository
import kotlinx.coroutines.launch



class MainScreenViewModel : ViewModel() {
    private val catRepo : ICatRepository = FirebaseCatRepo()
    private val userRepo : IUserAccountRepository = FireBaseUserRepo()
    private var currentCatId = -1 // the id the last seen cat, stored in the user account
    private var userCatId = -1 // the catId of the logged in user
    private var _userID : String = ""

    var mainPageState : MainState by mutableStateOf( MainState.Loading )

    fun setup(user : String) {
        if(user != _userID)
            viewModelScope.launch{
                getUserInfo(user)
                getCurrentCat()
            }
    }

    fun onAction(action : MainAction) {
        viewModelScope.launch {
            when(action)  {
                MainAction.OnDislike -> likeCatProfile(true)
                MainAction.OnLike -> likeCatProfile(false)
                MainAction.OnProfileView -> openCatProfile()
                MainAction.OnRetry -> getCurrentCat()
            }
        }
    }

    private suspend fun openCatProfile() {
        mainPageState = when(val result = catRepo.getCatProfileFromID(currentCatId)){
            is GetDbResult.Failure ->
                 MainState.Failure(result.error)
            is GetDbResult.Success -> {
                if(result.value is CatProfile){
                    MainState.ViewProfile(result.value)
                } else {
                    MainState.Failure("Got something from the repo, but it's not a cat profile.")
                }
            }
        }
    }

    private suspend fun likeCatProfile(liked : Boolean) {
        val likeSuccess = userRepo.addToLiked(_userID,currentCatId,liked)
        if(likeSuccess is SaveDbResult.Success)
            getNextCat()
        else
            mainPageState = MainState.Failure("Something failed...")

    }

    private suspend fun getCurrentCat() {
        if(currentCatId == userCatId)
            currentCatId += 1


        mainPageState = when (val result = catRepo.getCatProfileFromID(currentCatId)) {
            is GetDbResult.Failure -> {
                MainState.Failure(result.error)
            }

            is GetDbResult.Success -> {
                if (result.value is CatProfile){
                    MainState.Success(cat = result.value)
                } else {
                    MainState.Failure("Got something from the repo, but it's not a cat profile...")
                }
            }
        }
    }

    private suspend fun getNextCat ()  {

         currentCatId += 1
         if (currentCatId == userCatId)
             currentCatId += 1

         userRepo.changeViewedId(_userID,currentCatId)
         mainPageState = when (val result = catRepo.getCatProfileFromID(currentCatId)) {
             is GetDbResult.Failure -> {
                 MainState.Failure(result.error)
             }

             is GetDbResult.Success -> {
                 if (result.value is CatProfile){
                     MainState.Success(cat = result.value)
                 } else {
                     MainState.Failure("Got something from the repo, but it's not a cat profile...")
                 }
             }
         }
    }

    private suspend fun getUserInfo(userId: String){
        when(val u = userRepo.getLoggedInUser(userId)){
            is GetDbResult.Failure -> {
                mainPageState = MainState.Failure(error = u.error)
            }
            is GetDbResult.Success -> {
                if(u.value is UserProfile){
                    userCatId = u.value.catId
                    currentCatId = u.value.currentViewedId
                } else {
                    mainPageState = MainState.Failure(
                        "Got something from the repo, but it's not a cat profile..."
                    )
                }
            }
        }
    }


}