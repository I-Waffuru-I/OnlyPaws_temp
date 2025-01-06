package com.example.onlypaws.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult
import com.example.onlypaws.models.list.ListAction
import com.example.onlypaws.models.list.ListState
import com.example.onlypaws.repos.FireBaseUserRepo
import com.example.onlypaws.repos.IUserAccountRepository
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val _userRepo : IUserAccountRepository = FireBaseUserRepo()
    private var _userId : String = ""
    private var _displayLiked : Boolean = true

    var cats = mutableStateListOf<CatProfile>()
    var listPageState : ListState by mutableStateOf(ListState.Loading)

    fun doAction(action : ListAction) {
        viewModelScope.launch {
            when (action) {
                ListAction.OnRefresh -> {
                    Log.i("List VM","Refreshing...")
                }
                is ListAction.OnToggleLiked ->{
                    swapLikeForCat(action.catId)
                }
                ListAction.OnSwitchDisplayed -> {
                    _displayLiked = !_displayLiked
                }
            }
            refreshCatList()
        }
    }

    fun setup(userId : String) {
        _userId = userId
        viewModelScope.launch {
            refreshCatList()
        }
    }

    private suspend fun refreshCatList(){
        listPageState = when( val result = _userRepo.getAllLiked(_userId,_displayLiked)) {
            is GetDbResult.Failure ->
                ListState.Failure(result.error)
            is GetDbResult.Success -> {
                if (result.value is List<*>) {
                    cats.clear()
                    result.value.forEach {
                        if(it is CatProfile)
                            cats.add(it)
                    }
                    if(_displayLiked) ListState.SuccessLike
                    else ListState.SuccessDislike
                } else {
                     ListState.Failure("No cats found\r\n:(")
                }
            }
        }
    }

    private suspend fun swapLikeForCat(catId : Int){
         when(val result = _userRepo.removeFromLiked(_userId,catId,_displayLiked)) {
            is SaveDbResult.Failure ->
                listPageState = ListState.Failure(result.error)
            SaveDbResult.Success ->{
                Log.i("ListVM","Successfully swapped cat $catId")
            }
        }
    }
}