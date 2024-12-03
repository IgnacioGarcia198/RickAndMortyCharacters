package com.ignacio.rickandmorty.auth.presentation

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.SavedStateHandle
import com.ignacio.rickandmorty.auth.auth.google.AuthUiClient
import com.ignacio.rickandmorty.auth.domain.models.UserData
import com.ignacio.rickandmorty.kotlin_utils.coroutines.CoroutineTestRule
import com.ignacio.rickandmorty.kotlin_utils.exceptions.TestException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    @get: Rule
    val coroutineTestRule = CoroutineTestRule()
    private val testUserData =
        UserData(userId = "userid", username = "username", profilePictureUrl = "url")
    private val signIntentSenderRequest: IntentSenderRequest = mockk()
    private val authUiClient: AuthUiClient = mockk {
        every { getSignedInUser() }.returns(testUserData)
        coEvery { signInGoogle() }.returns(signIntentSenderRequest)
        coEvery { signInGoogleWithIntent(any()) }.returns(Result.success(testUserData))
    }
    private val savedStateHandle: SavedStateHandle = mockk()
    private val viewmodel: AuthViewModel = AuthViewModel(savedStateHandle, authUiClient)
    private val receivedUiStates = mutableListOf<SignInState>()
    private val launcher: ActivityResultLauncher<IntentSenderRequest> = mockk {
        every { launch(any()) }.just(runs)
    }
    private val mockIntent: Intent = mockk()
    private val githubLoginCall: suspend (AuthUiClient, String) -> Result<UserData?> =
        { _, _ -> Result.success(testUserData) }
    private val githubLoginCallSpy = spyk(githubLoginCall)

    @Before
    fun setUp() {
        receivedUiStates.clear()
        observeViewModel(viewmodel)
    }

    @Test
    fun `initial state is default`() = runBlocking {
        assertEquals(SignInState(), viewmodel.state.first())
    }

    @Test
    fun `getSignedInUser() uses AuthUiClient to retrieve signed user data`() {
        val user = viewmodel.getSignedInUser()

        verify { authUiClient.getSignedInUser() }
        assertEquals(testUserData, user)
    }

    @Test
    fun `startGoogleLogin() uses launcher and AuthUiClient to start Google login`() = runTest {
        viewmodel.startGoogleLogin(launcher)
        advanceUntilIdle()

        assertObservedStates(
            SignInState(),
            SignInState(status = Status.LOADING),
        )

        coVerify { authUiClient.signInGoogle() }
        verify { launcher.launch(signIntentSenderRequest) }
    }

    @Test
    fun `startGoogleLogin() sets state to error if start Google login fails`() = runTest {
        coEvery { authUiClient.signInGoogle() }.throws(TestException())

        viewmodel.startGoogleLogin(launcher)
        advanceUntilIdle()

        assertObservedStates(
            SignInState(),
            SignInState(status = Status.LOADING),
            SignInState(
                status = Status.LOADING,
                signInError = TestException(),
                isSignInSuccessful = false
            ),
        )

        coVerify { authUiClient.signInGoogle() }
        verify(exactly = 0) { launcher.launch(signIntentSenderRequest) }
    }

    @Test
    fun `handleSignInResult() uses AuthUiClient to sign in with Google`() = runTest {
        viewmodel.handleSignInResult(ActivityResult(Activity.RESULT_OK, mockIntent))
        advanceUntilIdle()

        coVerify { authUiClient.signInGoogleWithIntent(mockIntent) }
        assertObservedStates(
            SignInState(),
            SignInState(isSignInSuccessful = true, userData = testUserData),
        )
    }

    @Test
    fun `handleSignInResult() sets state to error if google sign fails`() = runTest {
        coEvery { authUiClient.signInGoogleWithIntent(any()) }.returns(Result.failure(TestException()))

        viewmodel.handleSignInResult(ActivityResult(Activity.RESULT_OK, mockIntent))
        advanceUntilIdle()

        coVerify { authUiClient.signInGoogleWithIntent(mockIntent) }
        assertObservedStates(
            SignInState(),
            SignInState(isSignInSuccessful = false, signInError = TestException()),
        )
    }

    @Test
    fun `startGithubLogin() uses AuthUiClient to sign in with Github`() = runTest {
        viewmodel.startGithubLogin(githubLoginCallSpy)
        advanceUntilIdle()

        coVerify { githubLoginCallSpy(authUiClient, "") }
        assertObservedStates(
            SignInState(),
            SignInState(status = Status.LOADING),
            SignInState(
                isSignInSuccessful = true,
                status = Status.LOADING,
                userData = testUserData
            ),
        )
    }

    @Test
    fun `startGithubLogin() sets state to error if sign in with Github fails`() = runTest {
        coEvery { githubLoginCallSpy(any(), any()) }.returns(Result.failure(TestException()))

        viewmodel.startGithubLogin(githubLoginCallSpy)
        advanceUntilIdle()

        coVerify { githubLoginCallSpy(authUiClient, "") }
        assertObservedStates(
            SignInState(),
            SignInState(status = Status.LOADING),
            SignInState(
                isSignInSuccessful = false,
                status = Status.LOADING,
                signInError = TestException()
            ),
        )
    }

    @Test
    fun `resetState() sets default state`() = runTest {
        viewmodel.handleSignInResult(ActivityResult(Activity.RESULT_OK, mockIntent))
        advanceUntilIdle()

        assertObservedStates(
            SignInState(),
            SignInState(isSignInSuccessful = true, userData = testUserData),
        )

        viewmodel.resetState()
        advanceUntilIdle()

        assertObservedStates(
            SignInState(),
            SignInState(isSignInSuccessful = true, userData = testUserData),
            SignInState(),
        )
    }

    private fun assertObservedStates(vararg states: SignInState) {
        assertEquals(states.toList(), receivedUiStates)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeViewModel(viewModel: AuthViewModel) {
        CoroutineScope(UnconfinedTestDispatcher()).launch {
            viewModel.state.collect {
                println("######## RECEIVED STATE: $it")
                receivedUiStates.add(it)
            }
        }
    }
}