package com.example.chambitassystemfront.ui.navigation

sealed class Screen(val route: String) {
    object SearchJobs : Screen("search_jobs")
    object JobDetail : Screen("job_detail/{jobId}") {
        fun createRoute(jobId: Int) = "job_detail/$jobId"
    }
    object Match : Screen("match")
    object JobStatus : Screen("job_status/{matchId}") {
        fun createRoute(matchId: Int) = "job_status/$matchId"
    }
    object JobCompleted : Screen("job_completed/{matchId}") {
        fun createRoute(matchId: Int) = "job_completed/$matchId"
    }
    object Review : Screen("review/{matchId}") {
        fun createRoute(matchId: Int) = "review/$matchId"
    }

    // Rutas para las pantallas nuevas
    object Home : Screen("home")
    object PublishJob : Screen("publish_job")
    object Messages : Screen("messages")
    object Profile : Screen("profile")
}
