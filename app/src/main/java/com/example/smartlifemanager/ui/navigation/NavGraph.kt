package com.example.smartlifemanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartlifemanager.data.preferences.AuthPreferences
import com.example.smartlifemanager.ui.screens.LoginScreen
import com.example.smartlifemanager.ui.screens.SplashScreen
import com.example.smartlifemanager.ui.screens.main.MainScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SmartLifeManagerNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val authPreferences = remember { AuthPreferences(context.applicationContext) }

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                getIsLoggedIn = {
                    authPreferences.getIsGuest() || FirebaseAuth.getInstance().currentUser != null
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToMain = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MAIN) {
            MainScreen()
        }
    }
}