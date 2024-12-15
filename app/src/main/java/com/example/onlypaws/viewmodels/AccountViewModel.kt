package com.example.onlypaws.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.account.AccountAction
import com.example.onlypaws.models.account.AccountStateList
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.repos.FireBaseUserRepo
import com.example.onlypaws.repos.FirebaseCatRepo
import com.example.onlypaws.repos.ICatRepository
import com.example.onlypaws.repos.IUserAccountRepository
import kotlinx.coroutines.launch


class AccountViewModel(userId : String) : ViewModel() {
    private val _userRepo : IUserAccountRepository = FireBaseUserRepo()
    private val _catRepo : ICatRepository = FirebaseCatRepo()
    private var _userId : String = userId

    var state : AccountStateList by mutableStateOf(AccountStateList.Loading)

    init {
       getProfile(_userId)
    }

    fun onAction (action : AccountAction){
        viewModelScope.launch {
            if(state is AccountStateList.Success)
            when (action) {
                AccountAction.OnLogOut -> state = AccountStateList.LogOut
                AccountAction.OnRetry -> getProfile(_userId)
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
                        AccountStateList.Success(result.value)
                    } else {
                        AccountStateList.Failure("Got something from the repo, but it's not a user profile...")
                    }
                }
            }
        }
    }

}