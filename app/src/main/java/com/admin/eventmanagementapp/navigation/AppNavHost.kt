package com.admin.eventmanagementapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.admin.eventmanagementapp.data.EventViewModel
import com.admin.eventmanagementapp.data.NotificationViewModel
import com.admin.eventmanagementapp.ui.screens.auth.LoginScreen
import com.admin.eventmanagementapp.ui.screens.auth.RegisterScreen
import com.admin.eventmanagementapp.ui.screens.event.CreateEventScreen
import com.admin.eventmanagementapp.ui.screens.event.EditEventScreen
import com.admin.eventmanagementapp.ui.screens.event.EventDetailsScreen
import com.admin.eventmanagementapp.ui.screens.event.EventListScreen
import com.admin.eventmanagementapp.ui.screens.home.HomeScreen
import com.admin.eventmanagementapp.ui.screens.notifications.NotificationScreen
import com.admin.eventmanagementapp.ui.screens.onboarding.OnboardingScreen
import com.admin.eventmanagementapp.ui.screens.profile.ProfileScreen
import com.admin.eventmanagementapp.ui.screens.settings.SettingsScreen
import com.admin.eventmanagementapp.ui.screens.splash.SplashScreen


@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    val eventViewModel: EventViewModel = viewModel()
    val notificationViewModel: NotificationViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // 🔵 SPLASH
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        // 🟣 ONBOARDING
        composable(Routes.ONBOARDING) {
            OnboardingScreen(navController)
        }

        // 🟠 AUTH
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        // 🟢 HOME
        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        // 📋 EVENT LIST
        composable(Routes.EVENT_LIST) {
            EventListScreen(
                navController = navController,
                viewModel = eventViewModel
            )
        }

        // ➕ CREATE EVENT
        composable(Routes.CREATE_EVENT) {
            CreateEventScreen(
                viewModel = eventViewModel,
                navController = navController
            )
        }

        // 📄 DETAILS
        composable("${Routes.EVENT_DETAILS}/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")

            EventDetailsScreen(
                eventId = eventId,
                navController = navController,
                viewModel = eventViewModel
            )
        }

        // ✏ EDIT
        composable("${Routes.EDIT_EVENT}/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")

            EditEventScreen(
                eventId = eventId,
                navController = navController,
                viewModel = eventViewModel
            )
        }

        // 🔔 NOTIFICATIONS
        composable(Routes.NOTIFICATIONS) {
            NotificationScreen(
                navController = navController,
                viewModel = notificationViewModel
            )
        }

        // 👤 PROFILE
        composable(Routes.PROFILE) {
            ProfileScreen(navController)
        }

        // ⚙ SETTINGS
        composable(Routes.SETTINGS) {
            SettingsScreen(navController)
        }
    }
}