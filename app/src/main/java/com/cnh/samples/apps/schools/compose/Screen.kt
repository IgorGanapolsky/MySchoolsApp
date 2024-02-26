package com.cnh.samples.apps.schools.compose

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {
    data object Home : Screen("home")

    data object SchoolDetail : Screen(
        route = "schoolDetail/{schoolId}",
        navArguments =
        listOf(
            navArgument("schoolId") {
                type = NavType.StringType
            },
        ),
    ) {
        fun createRoute(schoolId: String) = "schoolDetail/$schoolId"
    }

}
