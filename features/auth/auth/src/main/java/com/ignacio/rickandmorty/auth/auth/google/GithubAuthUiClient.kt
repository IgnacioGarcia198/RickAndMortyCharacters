package com.ignacio.rickandmorty.auth.auth.google

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.ignacio.rickandmorty.auth.domain.models.UserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GithubAuthUiClient @Inject constructor(
    private val auth: FirebaseAuth,
) {
    suspend fun startGitHubLogin(activity: Activity, email: String): Result<UserData?> {
        return kotlin.runCatching {
            val loginTask = auth.pendingAuthResult ?: let {
                val provider = OAuthProvider.newBuilder("github.com")
                provider.addCustomParameter("login", email)
                provider.scopes = listOf("user:email")
                auth.startActivityForSignInWithProvider(activity, provider.build())
            }

            val user = loginTask.await().user
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
}
