package com.cnh.samples.apps.schools.compose.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cnh.samples.apps.school.R
import com.cnh.samples.apps.schools.compose.allschools.AllSchoolsListScreen
import com.cnh.samples.apps.schools.compose.myschools.MySchoolsScreen
import com.cnh.samples.apps.schools.data.School
import com.cnh.samples.apps.schools.ui.CNHTheme
import kotlinx.coroutines.launch

enum class MySchoolsPage(
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int,
) {
    MY_SCHOOLS(R.string.my_schools_title, R.drawable.ic_my_schools),
    ALL_SCHOOLS_LIST(R.string.school_list_title, R.drawable.ic_school_list),
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSchoolClick: (School) -> Unit = {},
    pages: Array<MySchoolsPage> = MySchoolsPage.entries.toTypedArray(),
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopAppBar(scrollBehavior = scrollBehavior)
        },
    ) { contentPadding ->
        HomePagerScreen(
            onSchoolClick = onSchoolClick,
            pagerState = pagerState,
            pages = pages,
            Modifier.padding(top = contentPadding.calculateTopPadding()),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePagerScreen(
    onSchoolClick: (School) -> Unit,
    pagerState: PagerState,
    pages: Array<MySchoolsPage>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        val coroutineScope = rememberCoroutineScope()

        // Tab Row
        TabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            pages.forEachIndexed { index, page ->
                val title = stringResource(id = page.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title) },
                    icon = {
                        Icon(
                            painter = painterResource(id = page.drawableResId),
                            contentDescription = title,
                        )
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.secondary,
                )
            }
        }

        // Pages
        HorizontalPager(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) { index ->
            when (pages[index]) {
                MySchoolsPage.MY_SCHOOLS -> {
                    MySchoolsScreen(
                        Modifier.fillMaxSize(),
                        onAddSchoolClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(MySchoolsPage.ALL_SCHOOLS_LIST.ordinal)
                            }
                        },
                        onSchoolClick = {
                            onSchoolClick(it.school)
                        },
                    )
                }

                MySchoolsPage.ALL_SCHOOLS_LIST -> {
                    AllSchoolsListScreen(
                        onSchoolClick = onSchoolClick,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displaySmall,
                )
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun HomeScreenPreview() {
    CNHTheme {
        val pages = MySchoolsPage.entries.toTypedArray()
        HomePagerScreen(
            onSchoolClick = {},
            pagerState = rememberPagerState(pageCount = { pages.size }),
            pages = pages,
        )
    }
}
