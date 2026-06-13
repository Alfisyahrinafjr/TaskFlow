package com.example.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskflow.ui.components.TaskFlowBottomBar
import com.example.taskflow.ui.screens.AboutScreen
import com.example.taskflow.ui.screens.AddTaskScreen
import com.example.taskflow.ui.screens.ProgressScreen
import com.example.taskflow.ui.screens.SettingsScreen
import com.example.taskflow.ui.screens.TaskDetailScreen
import com.example.taskflow.ui.screens.TaskFlowHomeScreen
import com.example.taskflow.ui.screens.TaskFlowOnboardingScreen
import com.example.taskflow.ui.screens.TaskFlowSplashScreen
import com.example.taskflow.ui.theme.TaskFlowTheme
import com.example.taskflow.ui.viewmodel.TaskViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isGlobalDarkMode by remember { mutableStateOf(false) }
            var globalLanguage by remember { mutableStateOf("English (US)") }

            val configuration = LocalConfiguration.current
            val locale = if (globalLanguage == "Indonesia") Locale("in") else Locale("en")
            configuration.setLocale(locale)

            CompositionLocalProvider(LocalConfiguration provides configuration) {
                TaskFlowTheme(darkTheme = isGlobalDarkMode) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route

                        val showBottomBarRoutes = listOf(
                            Screen.Dashboard.route,
                            Screen.AddTask.route,
                            Screen.Progress.route,
                            Screen.Settings.route,
                            "${Screen.AddTask.route}?taskId={taskId}"
                        )

                        Scaffold(
                            bottomBar = {
                                if (currentRoute in showBottomBarRoutes) {
                                    TaskFlowBottomBar(navController = navController)
                                }
                            }
                        ) { innerPadding ->
                            Box(modifier = Modifier.padding(innerPadding)) {
                                NavHost(
                                    navController = navController,
                                    startDestination = Screen.Splash.route
                                ) {
                                    composable(Screen.Splash.route) {
                                        TaskFlowSplashScreen(onGetStartedClick = {
                                            navController.navigate(Screen.Onboarding.route) {
                                                popUpTo(Screen.Splash.route) { inclusive = true }
                                            }
                                        })
                                    }

                                    composable(Screen.Onboarding.route) {
                                        TaskFlowOnboardingScreen(onOnboardingComplete = {
                                            navController.navigate(Screen.Dashboard.route) {
                                                popUpTo(Screen.Onboarding.route) {
                                                    inclusive = true
                                                }
                                            }
                                        })
                                    }

                                    composable(Screen.Dashboard.route) {
                                        TaskFlowHomeScreen(
                                            viewModel = taskViewModel,
                                            onTaskClick = { taskId ->
                                                navController.navigate("task_detail_screen/$taskId")
                                            },
                                            onAddTaskClick = {
                                                navController.navigate(Screen.AddTask.route)
                                            }
                                        )
                                    }

                                    composable(
                                        route = Screen.TaskDetail.route,
                                        arguments = listOf(navArgument("taskId") {
                                            type = NavType.StringType
                                        })
                                    ) { backStackEntry ->
                                        val taskId = backStackEntry.arguments?.getString("taskId")
                                        TaskDetailScreen(
                                            taskId = taskId,
                                            viewModel = taskViewModel,
                                            onBackClick = {
                                                navController.popBackStack()
                                            },
                                            onEditClick = { id ->
                                                navController.navigate("${Screen.AddTask.route}?taskId=$id")
                                            }
                                        )
                                    }

                                    composable(
                                        route = "${Screen.AddTask.route}?taskId={taskId}",
                                        arguments = listOf(navArgument("taskId") {
                                            nullable = true
                                            defaultValue = null
                                        })
                                    ) { backStackEntry ->
                                        val taskId = backStackEntry.arguments?.getString("taskId")
                                        AddTaskScreen(
                                            viewModel = taskViewModel,
                                            onSaveSuccess = {
                                                navController.navigate(Screen.Dashboard.route) {
                                                    popUpTo(Screen.Dashboard.route) {
                                                        inclusive = true
                                                    }
                                                }
                                            },
                                            taskId = taskId
                                        )
                                    }

                                    composable(Screen.Progress.route) {
                                        ProgressScreen(
                                            viewModel = taskViewModel
                                        )
                                    }

                                    composable(Screen.Settings.route) {
                                        SettingsScreen(
                                            isDarkMode = isGlobalDarkMode,
                                            onDarkModeChange = { isGlobalDarkMode = it },
                                            currentLanguage = globalLanguage,
                                            onLanguageChange = { globalLanguage = it },
                                            onAboutClick = {
                                                navController.navigate(Screen.About.route)
                                            }
                                        )
                                    }

                                    composable(Screen.About.route) {
                                        AboutScreen(onBackClick = {
                                            navController.popBackStack()
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}