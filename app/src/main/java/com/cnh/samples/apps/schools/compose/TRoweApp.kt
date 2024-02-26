package com.cnh.samples.apps.schools.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cnh.samples.apps.schools.compose.home.HomeScreen
import com.cnh.samples.apps.schools.compose.schooldetail.SchoolDetailsScreen

@Composable
fun CNHApp() {
    val navController = rememberNavController()
    SchoolNavHost(
        navController = navController,
    )
}

@Composable
fun SchoolNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onSchoolClick = {
                    navController.navigate(
                        Screen.SchoolDetail.createRoute(schoolId = it.schoolDbn),
                    )
                },
            )
        }
        composable(
            route = Screen.SchoolDetail.route,
            arguments = Screen.SchoolDetail.navArguments,
        ) {
            SchoolDetailsScreen(
                onBackClick = { navController.navigateUp() },
            )
        }
    }
}