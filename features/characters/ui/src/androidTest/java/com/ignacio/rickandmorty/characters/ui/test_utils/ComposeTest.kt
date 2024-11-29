package com.ignacio.rickandmorty.characters.ui.test_utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ignacio.rickandmorty.android_utils.hilt.HiltTestActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// TODO: Move to a common ui module, but it cannot be testFixtures since its Kotlin Android so probably in debug sources.
abstract class ComposeTest {
    fun composeTest(
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