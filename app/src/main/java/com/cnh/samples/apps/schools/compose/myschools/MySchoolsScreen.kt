package com.cnh.samples.apps.schools.compose.myschools

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.cnh.samples.apps.school.R
import com.cnh.samples.apps.schools.data.MySchool
import com.cnh.samples.apps.schools.data.School
import com.cnh.samples.apps.schools.data.SchoolItems
import com.cnh.samples.apps.schools.ui.CNHTheme
import com.cnh.samples.apps.schools.viewmodels.MySchoolsTabViewModel
import com.cnh.samples.apps.schools.viewmodels.MySchoolsViewModel

@Composable
fun MySchoolsScreen(
    modifier: Modifier = Modifier,
    viewModel: MySchoolsTabViewModel = hiltViewModel(),
    onAddSchoolClick: () -> Unit,
    onSchoolClick: (SchoolItems) -> Unit,
) {
    val mySchools by viewModel.mySchoolItems.collectAsState(initial = emptyList())
    MySchoolsScreen(
        mySchools = mySchools,
        modifier = modifier,
        onAddSchoolClick = onAddSchoolClick,
        onSchoolClick = onSchoolClick,
    )
}

@Composable
fun MySchoolsScreen(
    mySchools: List<SchoolItems>,
    modifier: Modifier = Modifier,
    onAddSchoolClick: () -> Unit = {},
    onSchoolClick: (SchoolItems) -> Unit = {},
) {
    if (mySchools.isEmpty()) {
        EmptyMySchools(onAddSchoolClick, modifier)
    } else {
        MySchoolsList(mySchools = mySchools, onSchoolClick = onSchoolClick, modifier = modifier)
    }
}

@Composable
private fun MySchoolsList(
    mySchools: List<SchoolItems>,
    onSchoolClick: (SchoolItems) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Call reportFullyDrawn when the school list has been rendered
    val gridState = rememberLazyGridState()
    ReportDrawnWhen { gridState.layoutInfo.totalItemsCount > 0 }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier.imePadding(),
        state = gridState,
        contentPadding =
        PaddingValues(
            horizontal = dimensionResource(id = R.dimen.card_side_margin),
            vertical = dimensionResource(id = R.dimen.margin_normal),
        ),
    ) {
        items(
            items = mySchools,
            key = { it.school.schoolDbn },
        ) {
            MySchoolsListItem(schoolItems = it, onSchoolClick = onSchoolClick)
        }
    }
}

@Composable
private fun MySchoolsListItem(
    schoolItems: SchoolItems,
    onSchoolClick: (SchoolItems) -> Unit,
) {
    val vm = MySchoolsViewModel(schoolItems)

    // Dimensions
    val cardSideMargin = dimensionResource(id = R.dimen.card_side_margin)
    val marginNormal = dimensionResource(id = R.dimen.margin_normal)

    ElevatedCard(
        onClick = { onSchoolClick(schoolItems) },
        modifier =
        Modifier.padding(
            start = cardSideMargin,
            end = cardSideMargin,
            bottom = dimensionResource(id = R.dimen.card_bottom_margin),
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Column(Modifier.fillMaxWidth()) {
            /**
             * Igor
             * TODO: If given more time I'd dynamically fetch an image URL from the school's website using Google ML Kit.
             */
            /*GlideImage(
                model = vm.imageUrl,
                contentDescription = schoolItem.school.overviewParagraph,
                Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.school_item_image_height)),
                contentScale = ContentScale.Crop,
            )*/

            // School name
            Text(
                text = vm.schoolName,
                Modifier
                    .padding(vertical = marginNormal)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
private fun EmptyMySchools(
    onAddSchoolClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Calls reportFullyDrawn when this composable is composed.
    ReportDrawn()

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.school_empty),
            style = MaterialTheme.typography.headlineSmall,
        )
        Button(
            shape = MaterialTheme.shapes.medium,
            onClick = onAddSchoolClick,
        ) {
            Text(
                text = stringResource(id = R.string.add_school),
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}

@Preview
@Composable
private fun MySchoolsScreenPreview(
    @PreviewParameter(MySchoolsScreenPreviewParamProvider::class) mySchools: List<SchoolItems>,
) {
    CNHTheme {
        MySchoolsScreen(mySchools)
    }
}

private class MySchoolsScreenPreviewParamProvider : PreviewParameterProvider<List<SchoolItems>> {
    override val values: Sequence<List<SchoolItems>> =
        sequenceOf(
            emptyList(),
            listOf(
                SchoolItems(
                    school =
                    School(
                        schoolDbn = "1",
                        schoolName = "Spring Valley High School",
                        borough = "A School.",
                        overviewParagraph = "Students who are prepared for college must have an education that encourages them to take risks as they produce and perform. Our college preparatory curriculum develops writers and has built a tight-knit community. Our school develops students who can think analytically and write creatively. Our arts programming builds on our 25 years of experience in visual, performing arts and music on a middle school level. We partner with New Audience and the Whitney Museum as cultural partners. We are a International Baccalaureate (IB) candidate school that offers opportunities to take college courses at neighboring universities.",
                        academicOpportunities1 = "Free college courses at neighboring universities",
                        academicOpportunities2 = "International Travel, Special Arts Programs, Music, Internships, College Mentoring English Language Learner Programs: English as a New Language",
                        academicOpportunities3 = "The Learning to Work (LTW) partner for Liberation Diploma Plus High School is CAMBA.",
                        academicOpportunities4 = "PEARLS Awards, Academy Awards, Rose Ceremony/Parent Daughter Breakfast, Ice Cream Social.",
                        academicOpportunities5 = "Health and Wellness Program",
                        location = "10 East 15th Street, Manhattan NY 10003 (40.736526, -73.992727)",
                    ),
                    mySchools =
                    listOf(
                        MySchool(
                            schoolDbn = "02M260",
                            schoolName = "Clinton School Writers & Artists, M.S. 260",
                            schoolBorough = "Manhattan",
                        ),
                    ),
                ),
            ),
        )
}
