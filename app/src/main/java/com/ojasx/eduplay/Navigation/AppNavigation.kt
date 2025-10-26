package com.ojasx.eduplay.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.BottomBar.BottomBar
import com.ojasx.eduplay.BottomBar.Screens.PlaylistScreen
import com.ojasx.eduplay.BottomBar.Screens.SettingsScreen
import com.ojasx.eduplay.HomePage
import com.ojasx.eduplay.LoginOrSignUpPage.LoginPage.LoginPage
import com.ojasx.eduplay.LoginOrSignUpPage.SignUpPgae.SignUpPage
import com.ojasx.eduplay.MainScreen.MainScreen
import kotlinx.coroutines.flow.combine

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val playlistviewModel : PlaylistViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "BottomBar"
    ) {
        composable("BottomBar"){
            BottomBar(playlistviewModel,navController)
        }
        composable("MainScreen") {
            MainScreen(navController)
        }
        composable("LoginScreen") {
            LoginPage(navController)
        }
        composable("SignUpScreen") {
            SignUpPage(navController)
        }
        composable("Home") {
            HomePage(navController,playlistviewModel)
        }

        composable("PlaylistScreen"){
            PlaylistScreen(playlistviewModel,navController)
        }
        composable("SettingsScreen"){
            SettingsScreen(navController)
        }
    }
}