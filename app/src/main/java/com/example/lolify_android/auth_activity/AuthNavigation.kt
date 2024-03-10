package com.example.lolify_android.auth_activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lolify_android.data.SessionManager

@Composable
fun AuthNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginForm(navController = navController)
        }
        composable("register") {
            RegisterForm(navController = navController)
        }
    }
}