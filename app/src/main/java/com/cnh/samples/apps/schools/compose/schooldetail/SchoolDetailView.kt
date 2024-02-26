package com.cnh.samples.apps.schools.compose.schooldetail

import android.graphics.drawable.Drawable
import androidx.annotation.VisibleForTesting
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cnh.samples.apps.school.R
import com.cnh.samples.apps.schools.compose.Dimens
import com.cnh.samples.apps.schools.compose.utils.TextSnackbarContainer
import com.cnh.samples.apps.schools.data.School
import com.cnh.samples.apps.schools.ui.CNHTheme
import com.cnh.samples.apps.schools.viewmodels.SchoolDetailsViewModel

/**
 * As these callbacks are passed in through multiple Composables,to avoid having to name
 * parameters to not mix them up, they're aggregated in this class.
 */
data class SchoolDetailsCallbacks(
    val onFabClick: () -> Unit,
    val onBackClick: () -> Unit,
)

@Composable
fun SchoolDetailsScreen(
    schoolDetailsViewModel: SchoolDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val school = schoolDetailsViewModel.school.observeAsState().value
    val showSnackbar = schoolDetailsViewModel.showSnackbar.observeAsState().value

    if (school != null && showSnackbar != null) {
        Surface {
            TextSnackbarContainer(
                snackbarText = stringResource(R.string.added_school_to_my_schools),
                showSnackbar = showSnackbar,
                onDismissSnackbar = { schoolDetailsViewModel.dismissSnackbar() },
            ) {
                SchoolDetails(
                    school,
                    SchoolDetailsCallbacks(
                        onBackClick = onBackClick,
                        onFabClick = {
                            schoolDetailsViewModel.addSchoolToMySchools()
                        },
                    ),
                )
            }
        }
    }
}

@VisibleForTesting
@Composable
fun SchoolDetails(
    school: School,
    callbacks: SchoolDetailsCallbacks,
    modifier: Modifier = Modifier,
) {
    // SchoolDetails owns the scrollerPosition to simulate CollapsingToolbarLayout's behavior
    val scrollState = rememberScrollState()
    var schoolScroller by remember {
        mutableStateOf(SchoolDetailsScroller(scrollState, Float.MIN_VALUE))
    }
    val transitionState = remember(schoolScroller) { schoolScroller.toolbarTransitionState }
    val toolbarState = schoolScroller.getToolbarState(LocalDensity.current)

    // Transition that fades in/out the header with the image and the Toolbar
    val transition = updateTransition(transitionState, label = "")
    val toolbarAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        label = "",
    ) { toolbarTransitionState ->
        if (toolbarTransitionState == ToolbarState.HIDDEN) 0f else 1f
    }
    val contentAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        label = "",
    ) { toolbarTransitionState ->
        if (toolbarTransitionState == ToolbarState.HIDDEN) 1f else 0f
    }

    Box(modifier.fillMaxSize()) {
        SchoolDetailsContent(
            scrollState = scrollState,
            toolbarState = toolbarState,
            onNamePosition = { newNamePosition ->
                // Comparing to Float.MIN_VALUE as we are just interested on the original
                // position of name on the screen
                if (schoolScroller.namePosition == Float.MIN_VALUE) {
                    schoolScroller = schoolScroller.copy(namePosition = newNamePosition)
                }
            },
            school = school,
            imageHeight = with(LocalDensity.current) {
                val candidateHeight = Dimens.SchoolDetailAppBarHeight
                maxOf(candidateHeight, 1.dp)
            },
            onFabClick = callbacks.onFabClick,
            contentAlpha = { contentAlpha.value },
        )
        SchoolToolbar(
            toolbarState,
            school.schoolName,
            callbacks,
            toolbarAlpha = { toolbarAlpha.value },
            contentAlpha = { contentAlpha.value },
        )
    }
}

@Composable
private fun SchoolDetailsContent(
    scrollState: ScrollState,
    toolbarState: ToolbarState,
    school: School,
    imageHeight: Dp,
    onNamePosition: (Float) -> Unit,
    onFabClick: () -> Unit,
    contentAlpha: () -> Float,
) {
    Column(Modifier.verticalScroll(scrollState)) {
        ConstraintLayout {
            val (image, fab, _) = createRefs()

            SchoolImage(
                /** Igor - TODO with more time: dynamic school image loading */
                imageUrl = "data:image/jpeg;base64,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",
                imageHeight = imageHeight,
                modifier = Modifier
                    .constrainAs(image) { top.linkTo(parent.top) }
                    .alpha(contentAlpha()),
            )

            val fabEndMargin = Dimens.PaddingSmall
            SchoolFab(
                onFabClick = onFabClick,
                modifier = Modifier
                    .constrainAs(fab) {
                        centerAround(image.bottom)
                        absoluteRight.linkTo(
                            parent.absoluteRight,
                            margin = fabEndMargin,
                        )
                    }
                    .alpha(contentAlpha()),
            )

            SchoolInformation(
                name = school.schoolName,
                borough = school.borough,
                overview = school.overviewParagraph ?: "",
                academicOpportunities1 = school.academicOpportunities1 ?: "",
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun SchoolImage(
    /** Igor - TODO if given more time: implement dynamic image loading */
    imageUrl: String,
    imageHeight: Dp,
    modifier: Modifier = Modifier,
    placeholderColor: Color = MaterialTheme.colorScheme.onSurface.copy(0.2f),
) {
    var isLoading by remember { mutableStateOf(true) }
    Box(
        modifier
            .fillMaxWidth()
            .height(imageHeight),
    ) {
        if (isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(placeholderColor),
            )
        }

        /** Igor - TODO if given more time: implement dynamic image loading of schools */
        GlideImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        ) {
            it.addListener(
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean,
                    ): Boolean {
                        isLoading = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean,
                    ): Boolean {
                        isLoading = false
                        return false
                    }
                },
            )
        }
    }
}

@Composable
private fun SchoolFab(
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val addSchoolContentDescription = stringResource(R.string.add_school)

    /** Igor - TODO if given more time: dynamically hide Fab on My Schools items */
    FloatingActionButton(
        onClick = onFabClick,
        shape = MaterialTheme.shapes.small,
        modifier = modifier.semantics {
            contentDescription = addSchoolContentDescription
        },
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = null,
        )
    }
}

@Composable
private fun SchoolToolbar(
    toolbarState: ToolbarState,
    schoolName: String,
    callbacks: SchoolDetailsCallbacks,
    toolbarAlpha: () -> Float,
    contentAlpha: () -> Float,
) {
    if (toolbarState.isShown) {
        SchoolDetailsToolbar(
            schoolName = schoolName,
            onBackClick = callbacks.onBackClick,
            modifier = Modifier.alpha(toolbarAlpha()),
        )
    } else {
        SchoolHeaderActions(
            onBackClick = callbacks.onBackClick,
            modifier = Modifier.alpha(contentAlpha()),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SchoolDetailsToolbar(
    schoolName: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface {
        TopAppBar(modifier = modifier
            .statusBarsPadding()
            .background(color = MaterialTheme.colorScheme.surface), title = {
            Row {
                IconButton(
                    onBackClick,
                    Modifier.align(Alignment.CenterVertically),
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.a11y_back),
                    )
                }
                Text(
                    text = schoolName,
                    style = MaterialTheme.typography.titleLarge,
                    // As title in TopAppBar has extra inset on the left, need to do this: b/158829169
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                )
            }
        })
    }
}

@Composable
private fun SchoolHeaderActions(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(top = Dimens.ToolbarIconPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val iconModifier = Modifier
            .sizeIn(
                maxWidth = Dimens.ToolbarIconSize,
                maxHeight = Dimens.ToolbarIconSize,
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape,
            )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(start = Dimens.ToolbarIconPadding)
                .then(iconModifier),
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.a11y_back),
            )
        }
    }
}

@Composable
private fun SchoolInformation(
    name: String,
    borough: String,
    overview: String,
    academicOpportunities1: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(Dimens.PaddingLarge)) {
        Text(
            text = name,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .padding(
                    top = 264.dp,
                    start = Dimens.PaddingSmall,
                    end = Dimens.PaddingSmall,
                    bottom = Dimens.PaddingNormal,
                )
                .align(Alignment.CenterHorizontally)
        )
        Box(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    start = Dimens.PaddingSmall,
                    end = Dimens.PaddingSmall,
                    bottom = Dimens.PaddingNormal,
                ),
        ) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.servicing_needs_prefix),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = Dimens.PaddingSmall)
                        .align(Alignment.CenterHorizontally),
                )

                val boroText = "Boro: $borough"

                Text(
                    text = boroText,
                    modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = Dimens.PaddingSmall),
                )
            }
        }
        SchoolDescription(overview, academicOpportunities1)
    }
}

@Composable
private fun SchoolDescription(overview: String, opportunities1: String) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = overview,
            modifier =
            Modifier
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = "Academic Opportunities 1: $opportunities1",
            fontWeight = FontWeight.Bold,
            modifier =
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(Dimens.PaddingSmall)
        )
    }
}

@Preview
@Composable
private fun SchoolDetailContentPreview() {
    CNHTheme {
        Surface {
            SchoolDetails(
                School(
                    "02M260",
                    "Clinton School Writers & Artists, M.S. 260",
                    "M",
                    overviewParagraph = stringResource(id = R.string.mock_school_overview_paragraph),
                    academicOpportunities1 = stringResource(id = R.string.mock_academic_opportunities1),
                    academicOpportunities2 = stringResource(id = R.string.mock_academic_opportunities2),
                    academicOpportunities3 = stringResource(id = R.string.mock_academic_opportunities3),
                    academicOpportunities4 = stringResource(id = R.string.mock_academic_opportunities4),
                    academicOpportunities5 = stringResource(id = R.string.mock_academic_opportunities5),
                    location = "10 East 15th Street, Manhattan NY 10003 (40.736526, -73.992727)"
                ),
                SchoolDetailsCallbacks({ }, { }),
            )
        }
    }
}
