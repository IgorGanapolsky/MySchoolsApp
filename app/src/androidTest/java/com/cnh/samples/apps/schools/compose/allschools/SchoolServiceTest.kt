package com.cnh.samples.apps.schools.compose.allschools

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cnh.samples.apps.schools.compose.schooldetail.schoolForTesting
import com.cnh.samples.apps.schools.data.School
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SchoolServiceTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun schoolList_itemShown() {
        startSchoolList()
        composeTestRule.onNodeWithText("My Schools").assertIsDisplayed()
    }

    private fun startSchoolList(onSchoolClick: (School) -> Unit = {}) {
        composeTestRule.setContent {
            AllSchoolsListScreen(schools = listOf(schoolForTesting()), onSchoolClick = onSchoolClick)
        }
    }
}
