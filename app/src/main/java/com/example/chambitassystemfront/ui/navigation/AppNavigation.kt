package com.example.chambitassystemfront.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chambitassystemfront.ui.screens.jobs.JobCompletedScreen
import com.example.chambitassystemfront.ui.screens.jobs.JobDetailScreen
import com.example.chambitassystemfront.ui.screens.jobs.JobStatusScreen
import com.example.chambitassystemfront.ui.screens.jobs.SearchJobsScreen
import com.example.chambitassystemfront.ui.screens.match.MatchScreen
import com.example.chambitassystemfront.ui.screens.reviews.ReviewScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "search_jobs"
    ) {
        // 1. Buscar Trabajos
        composable("search_jobs") {
            SearchJobsScreen(
                onJobClick = { jobId ->
                    navController.navigate("job_detail/$jobId")
                }
            )
        }

        // 2. Detalle del Trabajo
        composable("job_detail/{jobId}") { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")?.toIntOrNull() ?: 0
            JobDetailScreen(
                jobId = jobId,
                onApplyClick = {
                    navController.navigate("match")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 3. Match
        composable("match") {
            MatchScreen(
                onGoToChat = {
                    navController.navigate("job_status")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Trabajo en Proceso
        composable("job_status") {
            JobStatusScreen(
                onGoToChat = {
                    navController.navigate("job_completed")
                },
                onViewDetails = {
                    navController.navigate("job_completed")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 5. Trabajo Completado
        composable("job_completed") {
            JobCompletedScreen(
                onGoToHome = {
                    navController.navigate("review")
                }
            )
        }

        // 6. Reseña y Calificación
        composable("review") {
            ReviewScreen(
                onSubmitReview = {
                    // Regresa al inicio limpiando el historial de navegación
                    navController.navigate("search_jobs") {
                        popUpTo("search_jobs") { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}