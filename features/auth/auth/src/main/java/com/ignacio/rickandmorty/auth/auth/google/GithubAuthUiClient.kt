package com.ignacio.rickandmorty.auth.auth.google

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.ignacio.rickandmorty.auth.domain.models.SignInResult
import com.ignacio.rickandmorty.auth.domain.models.UserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GithubAuthUiClient @Inject constructor(
    private val auth: FirebaseAuth,
) {
    suspend fun startGitHubLogin(activity: Activity, email: String): SignInResult {
        val provider = OAuthProvider.newBuilder("github.com")
        provider.addCustomParameter("login", email)
        provider.scopes = listOf("user:email")
        return try {
            val user = auth.startActivityForSignInWithProvider(activity, provider.build()).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                error = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                error = e
            )
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }
}
