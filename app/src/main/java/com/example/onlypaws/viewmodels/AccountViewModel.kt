package com.example.onlypaws.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.GetDbResult
import com.example.onlypaws.repos.FireBaseUserRepo
import com.example.onlypaws.repos.IUserAccountRepository
import kotlinx.coroutines.launch

sealed interface AccountState {
    data class Success(val account : UserProfile) : AccountState
    data object Loading : AccountState
    data class Failure(val error : String) : AccountState
}

class AccountViewModel(application : Application, userId : String) : ViewModel() {
    private val userRepo : IUserAccountRepository = FireBaseUserRepo()

    var state : AccountState by mutableStateOf(AccountState.Loading)

    init {
       getProfile(userId)
    }

    fun getProfile(id : String){
        viewModelScope.launch {
            val rslt = userRepo.getLoggedInUser(id)
            state = when (rslt) {
                is GetDbResult.Failure -> {
                    AccountState.Failure(rslt.error)
                }

                is GetDbResult.Success -> {
                    if (rslt.value is UserProfile){
                        AccountState.Success(rslt.value)
                    } else {
                        AccountState.Failure("Got something from the repo, but it's not a user profile...")
                    }
                }
            }
        }
    }

}