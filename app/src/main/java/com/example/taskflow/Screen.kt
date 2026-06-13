package com.example.taskflow

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Onboarding : Screen("onboarding_screen")
    object Dashboard : Screen("dashboard_screen")

    object TaskDetail : Screen("task_detail_screen/{taskId}")

    object AddTask : Screen("add_task_screen")
    object Progress : Screen("progress_screen")
    object Settings : Screen("settings_screen")
    object About : Screen("about_screen")
}