package com.ignacio.rickandmorty.ui.character.list.search

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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

        // enter criteria to search
        onNode(hasText(context.getString(R.string.type))).performTextInput("a")
        onNode(hasText(context.getString(R.string.name))).performTextInput("b")
        onNode(hasText(context.getString(R.string.species))).performTextInput("c")

        // check criteria is in place
        onNodeWithContentDescription(
            context.getString(
                R.string.advanced_search_enter_name,
                "b"
            )
        ).assertIsDisplayed()
        onNodeWithContentDescription(
            context.getString(
                R.string.advanced_search_enter_type,
                "a"
            )
        ).assertIsDisplayed()
        onNodeWithContentDescription(
            context.getString(
                R.string.advanced_search_enter_species,
                "c"
            )
        ).assertIsDisplayed()

        Espresso.pressBack() // close keyboard

        // select the character status
        selectStatus(CharacterListQueryCriteria.Status.DEAD)
        awaitIdle()
        // select the character gender
        selectGender(CharacterListQueryCriteria.Gender.MALE)
        awaitIdle()

        // close advanced search bottom sheet
        onNodeWithText(context.getString(R.string.close)).performClick()
        awaitIdle()
        // check selected character on the screen
        onNodeWithText("King Jellybean").assertIsDisplayed()
    }

    private suspend fun ComposeTestRule.selectStatus(status: CharacterListQueryCriteria.Status) {
        selectSpinner(
            currentValue = CharacterListQueryCriteria.Status.ANY,
            newValue = status,
            entries = CharacterListQueryCriteria.Status.entries,
            spinnerContentDescription = R.string.advanced_search_select_status,
            stringRepresentation = { it.localized(context) }
        )
    }

    private suspend fun ComposeTestRule.selectGender(gender: CharacterListQueryCriteria.Gender) {
        selectSpinner(
            currentValue = CharacterListQueryCriteria.Gender.ANY,
            newValue = gender,
            entries = CharacterListQueryCriteria.Gender.entries,
            spinnerContentDescription = R.string.advanced_search_select_gender,
            stringRepresentation = { it.localized(context) }
        )
    }

    private suspend fun <T : Any> ComposeTestRule.selectSpinner(
        currentValue: T,
        newValue: T,
        entries: List<T>,
        @StringRes spinnerContentDescription: Int,
        stringRepresentation: (T) -> String = { it.toString() }
    ) {
        // open status selector
        onNodeWithContentDescription(
            context.getString(
                spinnerContentDescription,
                stringRepresentation(currentValue)
            )
        ).performClick()
        // check it's open
        entries.forEach {
            onNode(hasTextExactly(stringRepresentation(it))).assertIsDisplayed()
        }
        // select
        onNode(hasTextExactly(stringRepresentation(newValue))).performClick()
        // confirm selection on screen
        onNodeWithContentDescription(
            context.getString(
                spinnerContentDescription,
                stringRepresentation(newValue)
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
