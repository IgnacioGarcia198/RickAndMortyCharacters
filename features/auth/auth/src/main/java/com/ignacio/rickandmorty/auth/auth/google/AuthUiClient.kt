package com.ignacio.rickandmorty.auth.auth.google

import android.app.Activity
import android.content.Intent
import androidx.activity.result.IntentSenderRequest
import com.google.firebase.auth.FirebaseAuth
import com.ignacio.rickandmorty.auth.domain.models.UserData
import javax.inject.Inject

class AuthUiClient @Inject constructor(
    private val githubAuthUiClient: GithubAuthUiClient,
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val auth: FirebaseAuth,
) {
    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    // Google
    suspend fun signInGoogle(): IntentSenderRequest =
        IntentSenderRequest.Builder(googleAuthUiClient.signIn()).build()

    suspend fun signInGoogleWithIntent(intent: Intent): Result<UserData?> =
        googleAuthUiClient.signInWithIntent(intent)

    suspend fun signOutGoogle() = googleAuthUiClient.signOut()

    // GitHub
    suspend fun startGitHubLogin(activity: Activity, email: String): Result<UserData?> =
        githubAuthUiClient.startGitHubLogin(activity, email)
}
