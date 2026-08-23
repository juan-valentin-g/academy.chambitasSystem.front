package com.example.chambitassystemfront.ui.navigation

sealed class Screen(val route: String) {
    object SearchJobs : Screen("search_jobs")
    object JobDetail : Screen("job_detail/{jobId}") {
        fun createRoute(jobId: Int) = "job_detail/$jobId"
    }
    object Match : Screen("match")
    object JobStatus : Screen("job_status")
    object JobCompleted : Screen("job_completed")
    object Review : Screen("review")

    // Rutas para las pantallas nuevas
    object Home : Screen("home")
    object PublishJob : Screen("publish_job")
    object Messages : Screen("messages")
    object Profile : Screen("profile")
}