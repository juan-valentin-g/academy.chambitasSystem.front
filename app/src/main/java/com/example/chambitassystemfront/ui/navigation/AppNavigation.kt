package com.example.chambitassystemfront.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.chambitassystemfront.ui.components.BottomNavBar
import com.example.chambitassystemfront.ui.navigation.Screen
import com.example.chambitassystemfront.ui.screens.*
import com.example.chambitassystemfront.ui.screens.jobs.*
import com.example.chambitassystemfront.ui.screens.match.MatchScreen
import com.example.chambitassystemfront.ui.screens.reviews.ReviewScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val routesWithBottomBar = listOf(
        Screen.SearchJobs.route,
        Screen.Home.route,
        Screen.PublishJob.route,
        Screen.Messages.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in routesWithBottomBar) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { targetRoute ->
                        navController.navigate(targetRoute) {
                            popUpTo(Screen.SearchJobs.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SearchJobs.route
        ) {
            // Pantalla de búsqueda
            composable(Screen.SearchJobs.route) {
                SearchJobsScreen(
                    onJobClick = { jobId ->
                        navController.navigate(Screen.JobDetail.createRoute(jobId))
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            // Detalles del trabajo
            composable(
                route = Screen.JobDetail.route,
                arguments = listOf(navArgument("jobId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getInt("jobId") ?: 0
                JobDetailScreen(
                    jobId = jobId,
                    onApplyClick = { navController.navigate(Screen.Match.route) },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Pantalla de Match
            composable(Screen.Match.route) {
                MatchScreen(
                    onGoToChat = { navController.navigate(Screen.JobStatus.route) },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Pantalla de Estatus del trabajo (CORREGIDA)
            composable(Screen.JobStatus.route) {
                JobStatusScreen(
                    onCompleteJobClick = { navController.navigate(Screen.JobCompleted.route) },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Pantalla de trabajo completado
            composable(Screen.JobCompleted.route) {
                JobCompletedScreen(
                    onGoToHome = { navController.navigate(Screen.Review.route) },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Pantalla de Reseñas
            composable(Screen.Review.route) {
                ReviewScreen(
                    onSubmitReview = {
                        navController.navigate(Screen.SearchJobs.route) {
                            popUpTo(Screen.SearchJobs.route) { inclusive = true }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Otras pantallas
            composable(Screen.Home.route) { HomeScreenPlaceholder() }
            composable(Screen.PublishJob.route) { PublishJobScreenPlaceholder() }
            composable(Screen.Messages.route) { MessagesScreenPlaceholder() }
            composable(Screen.Profile.route) { ProfileScreenPlaceholder() }
        }
    }
}