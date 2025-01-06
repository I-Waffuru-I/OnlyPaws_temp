package com.example.onlypaws.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.account.AccountAction
import com.example.onlypaws.models.account.AccountStateList
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.models.db.SaveDbResult
import com.example.onlypaws.repos.FireBaseUserRepo
import com.example.onlypaws.repos.FirebaseCatRepo
import com.example.onlypaws.repos.ICatRepository
import com.example.onlypaws.repos.IUserAccountRepository
import kotlinx.coroutines.launch


class AccountViewModel : ViewModel() {
    private val _userRepo : IUserAccountRepository = FireBaseUserRepo()
    private val _catRepo : ICatRepository = FirebaseCatRepo()
    private var _userId : String  = ""
    private var _catId : Int = -1

    var state : AccountStateList by mutableStateOf(AccountStateList.Loading)

    fun onAction (action : AccountAction){
        viewModelScope.launch {
            if(state is AccountStateList.Success)
            when (action) {
                AccountAction.OnLogOut -> state = AccountStateList.LogOut
                AccountAction.OnRetry -> getProfile(_userId)
                is AccountAction.OnSaveDescription -> saveValueToUser("description",action.description)
                is AccountAction.OnSaveImageLink -> saveValueToUser("image", action.link)
                is AccountAction.OnSaveUsername -> saveValueToUser("name", action.name)
            }
        }

    }
    fun loadPage(userId: String){
        getProfile(userId)
    }

    private fun getProfile(id : String){
        viewModelScope.launch {
            val result = _catRepo.getCatProfileFromEmail(id)
            state = when (result) {
                is GetDbResult.Failure -> {
                    AccountStateList.Failure(result.error)
                }

                is GetDbResult.Success -> {
                    if (result.value is CatProfile){
                        _catId = result.value.id
                        AccountStateList.Success(result.value)
                    } else {
                        AccountStateList.Failure("Got something from the repo, but it's not a user profile...")
                    }
                }
            }
        }
    }


    private fun saveValueToUser(property : String, value : String) {
            viewModelScope.launch {
                when (val result = _catRepo.updateCatProfileInformation(catId = _catId, property, value)) {
                    is SaveDbResult.Failure -> state = AccountStateList.Failure(result.error)
                    SaveDbResult.Success -> { Log.i("AccountUpdate","Saved the user values!") }
                }
            }

    }

}