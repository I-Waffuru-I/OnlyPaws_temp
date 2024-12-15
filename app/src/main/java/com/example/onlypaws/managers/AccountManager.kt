package com.example.onlypaws.managers

import android.app.Activity
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.example.onlypaws.models.CatProfile
import com.example.onlypaws.models.UserProfile
import com.example.onlypaws.models.db.SaveDbResult
import com.example.onlypaws.models.login.SignInResult
import com.example.onlypaws.models.register.SignUpResult
import com.example.onlypaws.models.registerDetails.DetailState
import com.example.onlypaws.repos.FireBaseUserRepo
import com.example.onlypaws.repos.IUserAccountRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class AccountManager(
    private val activity : Activity
) {

    private var auth = Firebase.auth
    private val credManager = CredentialManager.create(activity)
    private val firebaseUser : IUserAccountRepository = FireBaseUserRepo()




    suspend fun signUp(data : DetailState) : SignUpResult {

        return try {

            when(val result = firebaseUser.tryRegisterUser(data)){
                is SaveDbResult.Failure ->{
                    return SignUpResult.Failure(result.error)
                }
                SaveDbResult.Success -> {
                    credManager.createCredential(
                        context = activity,
                        request = CreatePasswordRequest(
                            id = data.email,
                            password = data.password,
                        )
                    )
                    auth.createUserWithEmailAndPassword(data.email,data.password)
                    return SignUpResult.Success(data.email)
                }
            }

        } catch (e : CreateCredentialCancellationException) {
            e.printStackTrace()
            SignUpResult.Cancelled
        } catch (e : CreateCredentialException) {
            e.printStackTrace()
            SignUpResult.Failure(e.errorMessage.toString())
        }
    }

    suspend fun signIn() : SignInResult {
        return try {
            val credentialResponse = credManager.getCredential(
                context = activity,
                request = GetCredentialRequest(
                    credentialOptions = listOf(GetPasswordOption())
                )
            )
            val credential = credentialResponse.credential as? PasswordCredential
                ?: return SignInResult.Failure("Credentials don't exist!")

            auth.signInWithEmailAndPassword(credential.id,credential.password)

            SignInResult.Success(credential.id)
        } catch (e : GetCredentialCancellationException) {
            e.printStackTrace()
            SignInResult.Cancelled
        } catch (e: NoCredentialException) {
            e.printStackTrace()
            SignInResult.NoCredentials
        } catch (e : GetCredentialException) {
            e.printStackTrace()
            SignInResult.Failure(e.errorMessage.toString())
        } catch (e : Exception) {
            e.printStackTrace()
            SignInResult.Failure(e.message.toString())
        }

    }



}