package com.ignacio.rickandmorty.ui.character.navigation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ignacio.rickandmorty.android_utils.hilt.HiltTestActivity
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui_common.theme.AppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun navigateToDetailAndBack() = runBlocking<Unit>(Dispatchers.Main) {
        with(composeTestRule) {
            setContent {
                AppTheme {
                    CharactersFeature()
                }
            }

            withContext(Dispatchers.Default) {
                waitUntil(timeoutMillis = 3000) {
                    onNodeWithText("Morty Smith").isDisplayed()
                }
                onNodeWithText("Morty Smith").performClick()
                onNodeWithText("Human").assertIsDisplayed()
                onNodeWithContentDescription(context.getString(R.string.navigate_back_arrow_content_desc)).performClick()
                onNodeWithText("Morty Smith").assertIsDisplayed()
            }
        }
    }
}
