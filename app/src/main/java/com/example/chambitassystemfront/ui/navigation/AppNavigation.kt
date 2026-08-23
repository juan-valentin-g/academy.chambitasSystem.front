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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


import com.example.chambitassystemfront.ui.screens.auth.ForgotPasswordScreen

import com.example.chambitassystemfront.ui.components.BottomNavBar
import com.example.chambitassystemfront.ui.screens.jobs.PostaJobScreen
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    /*
     * La barra inferior solamente aparece
     * en las pantallas principales del usuario.
     */
    val routesWithBottomBar = listOf(
        "home",
        "search_jobs",
        "publish_job",
        "messages",
        "profile"
    )

    Scaffold(
        bottomBar = {

            if (currentRoute in routesWithBottomBar) {

                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { targetRoute ->

                        navController.navigate(targetRoute) {

                            launchSingleTop = true
                            restoreState = true

                            popUpTo("home") {
                                saveState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier.padding(innerPadding)
        ) {

            // =========================================================
            // BIENVENIDA
            // =========================================================

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

// =========================================================
            // LOGIN
            // =========================================================

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
                    },

                    onForgotPasswordClick = {
                        navController.navigate("forgot_password")
                    }
                )
            }

            // =========================================================
            // RECUPERAR CONTRASEÑA
            // =========================================================

            composable("forgot_password") {
                ForgotPasswordScreen(
                    onBackToLogin = {
                        navController.popBackStack()
                    }
                )
            }

            // =========================================================
            // TIPO DE CUENTA
            // =========================================================

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

            // =========================================================
            // REGISTRO
            // =========================================================

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

            // =========================================================
            // REGISTRO EXITOSO
            // =========================================================

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

            // =========================================================
            // HOME
            // =========================================================

            composable("home") {

                HomeScreen(

                    onSearchClick = {
                        navController.navigate("search_jobs")
                    },

                    onPublishClick = {
                        navController.navigate("publish_job")
                    },

                    onProfileClick = {
                        navController.navigate("profile")
                    },

                    onChatClick = {
                        navController.navigate("messages")
                    }
                )
            }

            // =========================================================
            // BUSCAR TRABAJOS
            // =========================================================

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

            // =========================================================
// PUBLICAR TRABAJO
// =========================================================

            composable("publish_job") {

                PostaJobScreen(

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onPublishSuccess = {

                        navController.navigate("home") {

                            popUpTo("home") {
                                inclusive = false
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            // =========================================================
            // MENSAJES
            // =========================================================

            composable("messages") {

                ChatScreen(

                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            // =========================================================
            // DETALLE DEL TRABAJO
            // =========================================================

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

            // =========================================================
            // POSTULARSE
            // =========================================================

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

            // =========================================================
            // POSTULACIONES
            // =========================================================

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

            // =========================================================
            // MATCH
            // =========================================================

            composable("match") {

                MatchScreen(

                    onGoToChat = {
                        navController.navigate("messages")
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

            // =========================================================
            // TRABAJO EN PROCESO
            // =========================================================

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

            // =========================================================
            // TRABAJO COMPLETADO
            // =========================================================

            composable("job_completed") {

                JobCompletedScreen(

                    onGoToReview = {
                        navController.navigate("review")
                    }
                )
            }

            // =========================================================
            // RESEÑA
            // =========================================================

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

            // =========================================================
            // PERFIL
            // =========================================================

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

            // =========================================================
            // ADMINISTRADOR
            // =========================================================

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

            // =========================================================
            // CATEGORÍAS
            // =========================================================

            composable("categories") {

                CategoriesScreen(

                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}