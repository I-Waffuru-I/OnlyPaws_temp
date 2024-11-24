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
import com.example.onlypaws.models.login.SignInResult
import com.example.onlypaws.models.login.SignUpResult

class AccountManager(
    private val activity : Activity
) {

    private val credManager = CredentialManager.create(activity)

    suspend fun signUp(username : String, password : String) : SignUpResult {
        return try {
            credManager.createCredential(
                context = activity,
                request = CreatePasswordRequest(
                    id = username,
                    password = password,
                )
            )
            SignUpResult.Success(username)
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

            // Handle with firebase

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
        }
    }



}