package com.ignacio.rickandmorty.ui.character.list.search

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ignacio.rickandmorty.android_utils.hilt.HiltTestActivity
import com.ignacio.rickandmorty.domain.models.CharacterListQueryCriteria
import com.ignacio.rickandmorty.resources.R
import com.ignacio.rickandmorty.ui.character.mapping.localized
import com.ignacio.rickandmorty.ui.character.navigation.CharactersFeature
import com.ignacio.rickandmorty.ui_common.theme.AppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CharacterSearchTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun simpleCharacterSearch() = composeTest(
        composeTestRule = composeTestRule,
        content = {
            AppTheme {
                CharactersFeature()
            }
        },
        initiallyWaitFor = { onNodeWithText("Morty Smith").isDisplayed() },
    ) {
        onNodeWithContentDescription(context.getString(R.string.search)).performClick()
        onNode(hasText(context.getString(R.string.search))).assertIsDisplayed()
        onNode(hasText(context.getString(R.string.search))).performTextInput("Rick")
        onNode(hasText("Rick")).assertIsDisplayed()
        onNodeWithText("Rick Sanchez").assertIsDisplayed()
    }

    @Test
    fun advancedCharacterSearch() = composeTest(
        composeTestRule = composeTestRule,
        content = {
            AppTheme {
                CharactersFeature()
            }
        },
        initiallyWaitFor = { onNodeWithText("Morty Smith").isDisplayed() },
    ) {
        onNodeWithContentDescription(context.getString(R.string.search)).performClick()
        onNode(hasText(context.getString(R.string.search))).assertIsDisplayed()
        onNodeWithContentDescription(context.getString(R.string.advanced_search_title)).performClick()
        onNodeWithText(context.getString(R.string.advanced_search_title)).assertIsDisplayed()

        onNode(hasText(context.getString(R.string.type))).performTextInput("a")
        onNode(hasText(context.getString(R.string.name))).performTextInput("b")
        onNode(hasText(context.getString(R.string.species))).performTextInput("c")


        onNodeWithContentDescription(context.getString(R.string.advanced_search_enter_name, "b")).assertIsDisplayed()
        onNodeWithContentDescription(context.getString(R.string.advanced_search_enter_type, "a")).assertIsDisplayed()
        onNodeWithContentDescription(context.getString(R.string.advanced_search_enter_species, "c")).assertIsDisplayed()

        Espresso.pressBack() // close keyboard

        selectStatus(CharacterListQueryCriteria.Status.DEAD)


        onNodeWithText(context.getString(R.string.close)).performClick()
        awaitIdle()
        delay(5000)
    }

    private suspend fun ComposeTestRule.selectStatus(status: CharacterListQueryCriteria.Status) {
        // open status selector
        onNodeWithContentDescription(
            context.getString(
                R.string.advanced_search_select_status,
                CharacterListQueryCriteria.Status.ANY.localized(context)
            )
        ).performClick()
        awaitIdle()
        // check it's open
        CharacterListQueryCriteria.Status.entries.forEach {
            onNode(hasTextExactly(it.localized(context))).assertIsDisplayed()
        }
        waitForIdle()
        awaitIdle()
        // select
        onNode(hasTextExactly(status.localized(context))).performClick()
        waitForIdle()
        awaitIdle()
        onNodeWithContentDescription(
            context.getString(
                R.string.advanced_search_select_status,
                status.localized(context)
            )
        ).assertIsDisplayed()
    }

    private fun composeTest(
        composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<HiltTestActivity>, HiltTestActivity>,
        content: @Composable () -> Unit,
        initiallyWaitFor: (ComposeTestRule.() -> Boolean)? = null,
        initialWaitTimeout: Long = 3000,
        testBlock: suspend ComposeTestRule.() -> Unit,
    ) {
        runBlocking<Unit>(Dispatchers.Main) {
            with(composeTestRule) {
                setContent(content)
                withContext(Dispatchers.Default) {
                    initiallyWaitFor?.let {
                        waitUntil(timeoutMillis = initialWaitTimeout) {
                            initiallyWaitFor()
                        }
                    }
                    testBlock()
                }
            }
        }
    }
}
