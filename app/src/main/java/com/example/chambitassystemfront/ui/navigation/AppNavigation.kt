package com.example.chambitassystemfront.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.chambitassystemfront.ui.screens.admin.AdminUsersScreen
import com.example.chambitassystemfront.ui.screens.admin.AdminUsersViewModel
import com.example.chambitassystemfront.ui.screens.admin.AdminUsersViewModelFactory
import com.example.chambitassystemfront.ui.screens.admin.CategoriesScreen
import com.example.chambitassystemfront.ui.screens.applications.ApplicationsScreen
import com.example.chambitassystemfront.ui.screens.applications.ApplicationsViewModel
import com.example.chambitassystemfront.ui.screens.applications.ApplicationsViewModelFactory
import com.example.chambitassystemfront.data.repository.ApplicationsRepository
import com.example.chambitassystemfront.data.session.SessionManager
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
import com.example.chambitassystemfront.ui.screens.profile.EditProfileScreen
import com.example.chambitassystemfront.ui.screens.reviews.ReviewScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chambitassystemfront.ui.screens.home.CategoryViewModel
import com.example.chambitassystemfront.ui.screens.jobs.JobViewModel
import com.example.chambitassystemfront.ui.screens.jobs.JobViewModelFactory
import com.example.chambitassystemfront.data.repository.JobsRepository
import com.example.chambitassystemfront.data.repository.UserRepository
import com.example.chambitassystemfront.data.repository.MatchesRepository
import com.example.chambitassystemfront.data.repository.ReviewsRepository
import com.example.chambitassystemfront.ui.screens.match.MatchViewModel
import com.example.chambitassystemfront.ui.screens.match.MatchViewModelFactory
import com.example.chambitassystemfront.ui.screens.profile.ProfileViewModel
import com.example.chambitassystemfront.ui.screens.profile.ProfileViewModelFactory
import com.example.chambitassystemfront.ui.screens.reviews.ReviewViewModel
import com.example.chambitassystemfront.ui.screens.reviews.ReviewViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val categoryViewModel: CategoryViewModel = viewModel()

    // Declaramos el jobViewModel de forma global aquí para compartirlo entre pantallas
    val jobViewModel: JobViewModel = viewModel(factory = JobViewModelFactory(JobsRepository()))
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(UserRepository())
    )
    val matchViewModel: MatchViewModel = viewModel(
        factory = MatchViewModelFactory(MatchesRepository())
    )
    val reviewViewModel: ReviewViewModel = viewModel(
        factory = ReviewViewModelFactory(ReviewsRepository())
    )
    val adminUsersViewModel: AdminUsersViewModel = viewModel(
        factory = AdminUsersViewModelFactory(UserRepository())
    )

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
            startDestination = when {
                !SessionManager.hasActiveSession -> "welcome"
                SessionManager.userRole == "admin" -> "admin"
                else -> "home"
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            // BIENVENIDA
            composable("welcome") {
                WelcomeScreen(
                    onLoginClick = { navController.navigate("login") },
                    onRegisterClick = { navController.navigate("account_type") }
                )
            }

            // LOGIN
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("home") { popUpTo("welcome") { inclusive = true } }
                    },
                    onAdminLogin = {
                        navController.navigate("admin") { popUpTo("login") { inclusive = true } }
                    },
                    onRegisterClick = { navController.navigate("account_type") },
                    onBackClick = { navController.popBackStack() },
                    onForgotPasswordClick = { navController.navigate("forgot_password") }
                )
            }

            // RECUPERAR CONTRASEÑA
            composable("forgot_password") {
                ForgotPasswordScreen(onBackToLogin = { navController.popBackStack() })
            }

            // TIPO DE CUENTA
            composable("account_type") {
                AccountTypeScreen(
                    onContinue = { navController.navigate("register") },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // REGISTRO
            composable("register") {
                RegisterScreen(
                    onRegisterSuccess = { navController.navigate("register_success") },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // REGISTRO EXITOSO
            composable("register_success") {
                RegisterSuccessScreen(
                    onContinue = {
                        navController.navigate("login") { popUpTo("welcome") { inclusive = false } }
                    }
                )
            }

            // HOME
            composable("home") {
                HomeScreen(
                    categoryViewModel = categoryViewModel,
                    onSearchClick = { navController.navigate("search_jobs") },
                    onViewJobClick = { jobId -> navController.navigate("job_detail/$jobId") },
                    jobViewModel = jobViewModel
                )
            }

            // BUSCAR TRABAJOS
            composable("search_jobs") {
                SearchJobsScreen(
                    categoryViewModel = categoryViewModel,
                    jobViewModel = jobViewModel,
                    onJobClick = { jobId -> navController.navigate("job_detail/$jobId") },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // PUBLICAR TRABAJO
            composable("publish_job") {
                PostaJobScreen(
                    jobViewModel = jobViewModel,
                    categoryViewModel = categoryViewModel,
                    onBackClick = { navController.popBackStack() },
                    onPublishSuccess = {
                        navController.navigate("home") { popUpTo("home") { inclusive = false } }
                    }
                )
            }

            // MENSAJES
            composable(route = "messages") {
                ChatScreen(
                    jobId = 0,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                route = "messages/{jobId}",
                arguments = listOf(navArgument("jobId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getInt("jobId") ?: 0
                ChatScreen(
                    jobId = jobId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // DETALLE DEL TRABAJO
            composable(
                route = "job_detail/{jobId}",
                arguments = listOf(navArgument("jobId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getInt("jobId") ?: 0
                JobDetailScreen(
                    jobId = jobId,
                    jobViewModel = jobViewModel,
                    onApplyClick = { navController.navigate("apply_job/$jobId") },
                    onDeleteSuccess = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // POSTULARSE
            composable(
                route = "apply_job/{jobId}",
                arguments = listOf(navArgument("jobId") { type = NavType.IntType })
            ) { backStackEntry ->
                val jobId = backStackEntry.arguments?.getInt("jobId") ?: 0
                ApplyJobScreen(
                    jobId = jobId,
                    jobViewModel = jobViewModel,
                    onApplySuccess = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

// POSTULACIONES (MATCH Y SOLICITUDES)
            composable("applications") {
                val applicationsRepository = ApplicationsRepository()
                val applicationsViewModel: ApplicationsViewModel = viewModel(
                    factory = ApplicationsViewModelFactory(applicationsRepository)
                )

                ApplicationsScreen(
                    applicationsViewModel = applicationsViewModel,
                    jobViewModel = jobViewModel, // 🚀 Añade esta línea aquí para pasárselo a la pantalla
                    onMatchClick = { matchId ->
                        navController.navigate("match/$matchId")
                    },
                    onOpenMatchesClick = { navController.navigate("match") },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onJobClick = { jobId ->
                        navController.navigate("job_detail/$jobId")
                    }
                )
            }

// MATCH
            composable("match") {
                LaunchedEffect(Unit) {
                    matchViewModel.fetchMatches()
                }

                val currentMatch =
                    matchViewModel.selectedMatch ?: matchViewModel.matches.firstOrNull()

                MatchScreen(
                    match = currentMatch,
                    availableMatches = matchViewModel.matches,
                    isLoading = matchViewModel.isLoading,
                    errorMessage = matchViewModel.errorMessage,
                    onSelectMatch = { matchViewModel.fetchMatch(it) },
                    onOpenStatus = { matchId ->
                        navController.navigate("job_status/$matchId")
                    },
                    onGoToHome = {
                        navController.navigate("home") { popUpTo("home") { inclusive = false } }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                route = "match/{matchId}",
                arguments = listOf(navArgument("matchId") { type = NavType.IntType })
            ) { backStackEntry ->
                val matchId = backStackEntry.arguments?.getInt("matchId") ?: 0
                LaunchedEffect(matchId) {
                    matchViewModel.fetchMatch(matchId)
                }

                MatchScreen(
                    match = matchViewModel.selectedMatch?.takeIf { it.id == matchId },
                    isLoading = matchViewModel.isLoading,
                    errorMessage = matchViewModel.errorMessage,
                    onOpenStatus = { id -> navController.navigate("job_status/$id") },
                    onGoToHome = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = false }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // TRABAJO EN PROCESO
            composable(
                route = "job_status/{matchId}",
                arguments = listOf(navArgument("matchId") { type = NavType.IntType })
            ) { backStackEntry ->
                val matchId = backStackEntry.arguments?.getInt("matchId") ?: 0
                LaunchedEffect(matchId) {
                    if (matchViewModel.selectedMatch?.id != matchId) {
                        matchViewModel.fetchMatch(matchId)
                    }
                }

                JobStatusScreen(
                    match = matchViewModel.selectedMatch?.takeIf { it.id == matchId },
                    isLoading = matchViewModel.isLoading,
                    errorMessage = matchViewModel.errorMessage,
                    onCompleteMatch = { id ->
                        matchViewModel.completeMatch(
                            matchId = id,
                            onSuccess = {
                                navController.navigate("job_completed/$id")
                            },
                            onError = {}
                        )
                    },
                    onReviewClick = { id -> navController.navigate("review/$id") },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // TRABAJO COMPLETADO
            composable(
                route = "job_completed/{matchId}",
                arguments = listOf(navArgument("matchId") { type = NavType.IntType })
            ) { backStackEntry ->
                val matchId = backStackEntry.arguments?.getInt("matchId") ?: 0
                LaunchedEffect(matchId) {
                    if (matchViewModel.selectedMatch?.id != matchId) {
                        matchViewModel.fetchMatch(matchId)
                    }
                }
                val currentMatch = matchViewModel.selectedMatch?.takeIf { it.id == matchId }

                JobCompletedScreen(
                    job = currentMatch?.job,
                    onGoToReview = { navController.navigate("review/$matchId") }
                )
            }

            // RESEÑA
            composable(
                route = "review/{matchId}",
                arguments = listOf(navArgument("matchId") { type = NavType.IntType })
            ) { backStackEntry ->
                val matchId = backStackEntry.arguments?.getInt("matchId") ?: 0
                LaunchedEffect(matchId) {
                    if (matchViewModel.selectedMatch?.id != matchId) {
                        matchViewModel.fetchMatch(matchId)
                    }
                }

                ReviewScreen(
                    match = matchViewModel.selectedMatch?.takeIf { it.id == matchId },
                    reviewViewModel = reviewViewModel,
                    onSubmitReview = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = false }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // PERFIL
            composable("profile") {
                LaunchedEffect(Unit) {
                    profileViewModel.fetchProfile()
                    reviewViewModel.fetchReceivedReviews()
                    matchViewModel.fetchMatches()
                }

                ProfileScreen(
                    user = profileViewModel.user,
                    receivedReviews = reviewViewModel.receivedReviews,
                    completedJobsCount = matchViewModel.matches.count {
                        it.estado == "FINALIZADO"
                    },
                    onBackClick = { navController.popBackStack() },
                    onApplicationsClick = { navController.navigate("applications") },
                    onMatchesClick = { navController.navigate("match") },
                    onEditProfileClick = { navController.navigate("edit_profile") },
                    onLogoutClick = {
                        SessionManager.clearSession()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            // EDITAR PERFIL
            composable("edit_profile") {
                LaunchedEffect(Unit) {
                    if (profileViewModel.user == null) {
                        profileViewModel.fetchProfile()
                    }
                }
                val currentUser = profileViewModel.user

                EditProfileScreen(
                    initialName = currentUser?.nombre ?: "",
                    initialPhone = currentUser?.telefono ?: "",
                    initialDescription = currentUser?.descripcion ?: "",
                    onSaveProfile = { name, phone, description ->
                        profileViewModel.updateProfile(
                            name = name,
                            phone = phone,
                            description = description,
                            onSuccess = {
                                navController.popBackStack()
                            },
                            onError = { _ -> }
                        )
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // ADMINISTRADOR
            composable("admin") {
                AdminDashboardScreen(
                    onUsersClick = { navController.navigate("admin_users") },
                    onCategoriesClick = { navController.navigate("categories") },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable("admin_users") {
                AdminUsersScreen(
                    viewModel = adminUsersViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // CATEGORÍAS
            composable("categories") {
                CategoriesScreen(
                    categoryViewModel = categoryViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
