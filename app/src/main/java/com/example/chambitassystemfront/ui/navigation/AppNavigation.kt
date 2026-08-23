package com.example.chambitassystemfront.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.chambitassystemfront.ui.screens.admin.AdminDashboardScreen
import com.example.chambitassystemfront.ui.screens.admin.CategoriesScreen
import com.example.chambitassystemfront.ui.screens.applications.ApplicationsScreen
import com.example.chambitassystemfront.ui.screens.auth.AccountTypeScreen
import com.example.chambitassystemfront.ui.screens.auth.LoginScreen
import com.example.chambitassystemfront.ui.screens.auth.RegisterScreen
import com.example.chambitassystemfront.ui.screens.auth.RegisterSuccessScreen
import com.example.chambitassystemfront.ui.screens.auth.WelcomeScreen
import com.example.chambitassystemfront.ui.screens.chat.ChatScreen
import com.example.chambitassystemfront.ui.screens.home.HomeScreen
import com.example.chambitassystemfront.ui.screens.jobs.ApplyJobScreen
import com.example.chambitassystemfront.ui.screens.jobs.JobCompletedScreen
import com.example.chambitassystemfront.ui.screens.jobs.JobDetailScreen
import com.example.chambitassystemfront.ui.screens.jobs.JobStatusScreen
import com.example.chambitassystemfront.ui.screens.jobs.SearchJobsScreen
import com.example.chambitassystemfront.ui.screens.match.MatchScreen
import com.example.chambitassystemfront.ui.screens.profile.ProfileScreen
import com.example.chambitassystemfront.ui.screens.reviews.ReviewScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") {

            WelcomeScreen(
                onLoginClick = {
                    navController.navigate("login")
                },
                onRegisterClick = {
                    navController.navigate("account_type")
                }
            )
        }


        composable("login") {

            LoginScreen(

                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onAdminLogin = {
                    navController.navigate("admin") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onRegisterClick = {
                    navController.navigate("account_type")
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("account_type") {

            AccountTypeScreen(

                onContinue = {
                    navController.navigate("register")
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("register") {

            RegisterScreen(

                onRegisterSuccess = {
                    navController.navigate("register_success")
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("register_success") {

            RegisterSuccessScreen(

                onContinue = {

                    navController.navigate("login") {

                        popUpTo("welcome") {
                            inclusive = false
                        }
                    }
                }
            )
        }


        composable("home") {

            HomeScreen(

                onSearchClick = {
                    navController.navigate("search_jobs")
                },

                onPublishClick = {
                    navController.navigate("applications")
                },

                onProfileClick = {
                    navController.navigate("profile")
                },

                onChatClick = {
                    navController.navigate("chat")
                }
            )
        }


        composable("search_jobs") {

            SearchJobsScreen(

                onJobClick = { jobId ->
                    navController.navigate("job_detail/$jobId")
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            route = "job_detail/{jobId}",

            arguments = listOf(
                navArgument("jobId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val jobId =
                backStackEntry.arguments?.getInt("jobId") ?: 0

            JobDetailScreen(

                jobId = jobId,

                onApplyClick = {
                    navController.navigate("apply_job/$jobId")
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "apply_job/{jobId}",

            arguments = listOf(
                navArgument("jobId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val jobId =
                backStackEntry.arguments?.getInt("jobId") ?: 0

            ApplyJobScreen(

                jobId = jobId,

                onApplySuccess = {
                    navController.navigate("applications")
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("applications") {

            ApplicationsScreen(

                onMatchClick = {
                    navController.navigate("match")
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("match") {

            MatchScreen(

                onGoToChat = {
                    navController.navigate("chat")
                },

                onGoToHome = {

                    navController.navigate("home") {

                        popUpTo("home") {
                            inclusive = false
                        }
                    }
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("chat") {

            ChatScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("job_status") {

            JobStatusScreen(

                onCompleteJob = {
                    navController.navigate("job_completed")
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("job_completed") {

            JobCompletedScreen(

                onGoToReview = {
                    navController.navigate("review")
                }
            )
        }


        composable("review") {

            ReviewScreen(

                onSubmitReview = {

                    navController.navigate("home") {

                        popUpTo("home") {
                            inclusive = false
                        }
                    }
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("profile") {

            ProfileScreen(

                onBackClick = {
                    navController.popBackStack()
                },

                onApplicationsClick = {
                    navController.navigate("applications")
                }
            )
        }

        composable("admin") {

            AdminDashboardScreen(

                onCategoriesClick = {
                    navController.navigate("categories")
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("categories") {

            CategoriesScreen(

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}