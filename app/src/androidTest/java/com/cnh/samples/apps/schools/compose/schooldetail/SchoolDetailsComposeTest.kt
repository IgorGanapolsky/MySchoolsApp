package com.cnh.samples.apps.schools.compose.schooldetail

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cnh.samples.apps.school.R
import com.cnh.samples.apps.schools.data.School
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SchoolDetailsComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mySchools_checkIsEmpty() {
        startSchoolDetails()
        composeTestRule.onNodeWithText("My Schools").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Add school").assertIsDisplayed()
    }

    @Test
    fun mySchools_checkIsNotEmpty() {
        startSchoolDetails()
        composeTestRule.onNodeWithText("My Schools").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Add School").assertDoesNotExist()
    }

    private fun startSchoolDetails() {
        composeTestRule.setContent {
            SchoolDetails(
                school = schoolForTesting(),
                callbacks = SchoolDetailsCallbacks({ }, { })
            )
        }
    }
}

@Composable
internal fun schoolForTesting(): School {
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    val mockOverviewParagraph = context.resources.getString(R.string.mock_school_overview_paragraph)

    return School(
        schoolDbn = "02M260",
        schoolName = "Clinton School Writers & Artists, M.S. 260",
        borough = "M",
        overviewParagraph = mockOverviewParagraph,
        academicOpportunities1 = "Free college courses at neighboring universities",
        academicOpportunities2 = "International Travel, Special Arts Programs, Music, Internships, College Mentoring English Language Learner Programs: English as a New Language",
        academicOpportunities3 = "The Learning to Work (LTW) partner for Liberation Diploma Plus High School is CAMBA.",
        academicOpportunities4 = "PEARLS Awards, Academy Awards, Rose Ceremony/Parent Daughter Breakfast, Ice Cream Social.",
        academicOpportunities5 = "Health and Wellness Program",
        location = "10 East 15th Street, Manhattan NY 10003 (40.736526, -73.992727)"
    )
}