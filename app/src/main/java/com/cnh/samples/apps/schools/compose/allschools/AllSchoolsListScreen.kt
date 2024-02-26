package com.cnh.samples.apps.schools.compose.allschools

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.cnh.samples.apps.school.R
import com.cnh.samples.apps.schools.data.School
import com.cnh.samples.apps.schools.viewmodels.SchoolsViewModel

@Composable
fun AllSchoolsListScreen(
    onSchoolClick: (School) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SchoolsViewModel = hiltViewModel(),
) {
    val schools by viewModel.schools.observeAsState(initial = emptyList())
    AllSchoolsListScreen(schools = schools, modifier, onSchoolClick = onSchoolClick)
}

@Composable
fun AllSchoolsListScreen(
    schools: List<School>,
    modifier: Modifier = Modifier,
    onSchoolClick: (School) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier =
        modifier
            .testTag("schools_list")
            .imePadding(),
        contentPadding =
        PaddingValues(
            horizontal = dimensionResource(id = R.dimen.card_side_margin),
            vertical = dimensionResource(id = R.dimen.header_margin),
        ),
    ) {
        items(
            items = schools,
            key = { it.schoolDbn },
        ) { school ->
            SchoolListItem(school = school) {
                onSchoolClick(school)
            }
        }
    }
}

@Preview
@Composable
private fun SchoolsListScreenPreview(
    @PreviewParameter(SchoolsListPreviewParamProvider::class) schools: List<School>,
) {
    AllSchoolsListScreen(schools = schools)
}

private class SchoolsListPreviewParamProvider : PreviewParameterProvider<List<School>> {
    override val values: Sequence<List<School>> =
        sequenceOf(
            emptyList(),
            listOf(
                School(
                    schoolDbn = "02M260",
                    schoolName = "Clinton School Writers & Artists, M.S. 260",
                    borough = "M",
                    overviewParagraph = "Students who are prepared for college must have an education that encourages them to take risks as they produce and perform. Our college preparatory curriculum develops writers and has built a tight-knit community. Our school develops students who can think analytically and write creatively. Our arts programming builds on our 25 years of experience in visual, performing arts and music on a middle school level. We partner with New Audience and the Whitney Museum as cultural partners. We are a International Baccalaureate (IB) candidate school that offers opportunities to take college courses at neighboring universities.",
                    academicOpportunities1 = "Free college courses at neighboring universities",
                    academicOpportunities2 = "International Travel, Special Arts Programs, Music, Internships, College Mentoring English Language Learner Programs: English as a New Language",
                    location = "10 East 15th Street, Manhattan NY 10003 (40.736526, -73.992727)"
                ),

                School(
                    schoolDbn = "21K728",
                    schoolName = "Liberation Diploma Plus High School",
                    borough = "K",
                    overviewParagraph = "The mission of Liberation Diploma Plus High School, in partnership with CAMBA, is to develop the student academically, socially, and emotionally. We will equip students with the skills needed to evaluate their options so that they can make informed and appropriate choices and create personal goals for success. Our year-round model (trimesters plus summer school) provides students the opportunity to gain credits and attain required graduation competencies at an accelerated rate. Our partners offer all students career preparation and college exposure. Students have the opportunity to earn college credit(s). In addition to fulfilling New York City graduation requirements, students are required to complete a portfolio to receive a high school diploma.",
                    academicOpportunities1 = "Learning to Work, Student Council, Advisory Leadership, School Newspaper, Community Service Group, School Leadership Team, Extended Day/PM School, College Now",
                    academicOpportunities2 = "CAMBA, Diploma Plus, Medgar Evers College, Coney Island Genera on Gap, Urban Neighborhood Services, Coney Island Coalition Against Violence, I Love My Life Initiative, New York City Police Department",
                    academicOpportunities3 = "The Learning to Work (LTW) partner for Liberation Diploma Plus High School is CAMBA.",
                    location = "2865 West 19th Street, Brooklyn, NY 11224 (40.576976, -73.985413)"
                ),

                School(
                    schoolDbn = "08X282",
                    schoolName = "Women's Academy of Excellence",
                    borough = "X",
                    overviewParagraph = "The Women’s Academy of Excellence is an all-girls public high school, serving grades 9-12. Our mission is to create a community of lifelong learners, to nurture the intellectual curiosity and creativity of young women and to address their developmental needs. The school community cultivates dynamic, participatory learning, enabling students to achieve academic success at many levels, especially in the fields of math, science, and civic responsibility. Our scholars are exposed to a challenging curriculum that encourages them to achieve their goals while being empowered to become young women and leaders. Our Philosophy is GIRLS MATTER!",
                    academicOpportunities1 = "Genetic Research Seminar, Touro College Partnership, L'Oreal Roll Model Program, Town Halls, Laptop carts, SMART Boards in every room, Regents Prep.",
                    academicOpportunities2 = "WAE Bucks Incentive Program, Monroe College JumpStart, National Hispanic Honor Society, National Honor Society,Lehman College Now, Castle Learning.",
                    academicOpportunities3 = "Pupilpath, Saturday school, Leadership class, College Trips, Teen Empowerment Series, College Fairs, Anti-bullying Day, Respect for All, Career Day.",
                    academicOpportunities4 = "PEARLS Awards, Academy Awards, Rose Ceremony/Parent Daughter Breakfast, Ice Cream Social.",
                    academicOpportunities5 = "Health and Wellness Program",
                    location = "456 White Plains Road, Bronx NY 10473 (40.815043, -73.85607)"
                ),
            ),
        )
}
