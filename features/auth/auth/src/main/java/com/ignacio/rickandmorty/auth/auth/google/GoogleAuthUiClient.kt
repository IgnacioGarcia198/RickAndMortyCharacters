package com.ignacio.rickandmorty.auth.auth.google

import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ignacio.rickandmorty.auth.domain.models.UserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException


private const val WEB_CLIENT_ID =
    "290149356907-bn871n1qv1kktb5g2c4ni8g6flqtstl5.apps.googleusercontent.com"

class GoogleAuthUiClient @Inject constructor(
    private val oneTapClient: SignInClient,
    private val auth: FirebaseAuth,
) {
    suspend fun signIn(): IntentSender {
        return oneTapClient.beginSignIn(
            buildSignInRequest()
        ).await().pendingIntent.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): Result<UserData?> {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return kotlin.runCatching {
            val user = auth.signInWithCredential(googleCredentials).await().user
            user?.run {
                UserData(
                    userId = uid,
                    username = displayName,
                    profilePictureUrl = photoUrl?.toString()
                )
            }
        }.onFailure { e ->
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(WEB_CLIENT_ID)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}