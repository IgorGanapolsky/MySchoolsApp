package com.cnh.samples.apps.schools

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class MySchoolsActivityTestService {
    private val hiltRule = HiltAndroidRule(this)
    private val composeTestRule = createAndroidComposeRule<SchoolsActivity>()

    @get:Rule
    val rule: RuleChain =
        RuleChain
            .outerRule(hiltRule)
            .around(composeTestRule)

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config =
            Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setExecutor(SynchronousExecutor())
                .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun clickAddSchool_OpensSchoolsList() {
        // Given that no schools are added to the user's My Schools

        // When the "Add School" button is clicked
        with(composeTestRule.onNodeWithText("Add School")) {
            assertExists()
            assertIsDisplayed()
            performClick()
        }

        composeTestRule.waitForIdle()

        // Then the pager should change to the School List page
        with(composeTestRule.onNodeWithTag("school_list")) {
            assertExists()
        }
    }
}
