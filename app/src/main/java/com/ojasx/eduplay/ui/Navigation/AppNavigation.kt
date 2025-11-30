package com.ojasx.eduplay.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.ui.BottomBar.BottomBar
import com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.PlaylistScreen
import com.ojasx.eduplay.ui.BottomBar.Screens.SettingsScreen.SettingsScreen
import com.ojasx.eduplay.ui.HelpAndSupport.ReportABug.ReportABugMainScreen
import com.ojasx.eduplay.ui.HelpAndSupport.ReportABug.ReportBugButton
import com.ojasx.eduplay.ui.HomePage
import com.ojasx.eduplay.LoginOrSignUpPage.LoginPage.LoginPage
import com.ojasx.eduplay.LoginOrSignUpPage.SignUpPgae.SignUpPage
import com.ojasx.eduplay.ui.MainScreen.MainScreen
import com.ojasx.eduplay.Notifications.LocalNotifications.ShowLocalNotificationScreen
import com.ojasx.eduplay.ViewModel.ProfileViewModel
import com.ojasx.eduplay.ui.profile.UserProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val playlistviewModel : PlaylistViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination ="BottomBar"
    ) {
        composable("BottomBar"){
            BottomBar(playlistviewModel,profileViewModel,navController)
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
            HomePage(navController,playlistviewModel,profileViewModel)
        }

        composable("PlaylistScreen"){
            PlaylistScreen(playlistviewModel,navController)
        }
        composable("SettingsScreen"){
            SettingsScreen(navController,profileViewModel)
        }
        composable("ShowLocalNotificationScreen"){
            ShowLocalNotificationScreen(navController)
        }
        composable("ReportABugMainScreen"){
            ReportABugMainScreen(navController)
        }
        composable("ReportBugButton"){
            ReportBugButton()
        }
        composable("UserProfileScreen"){
            UserProfileScreen(navController, profileViewModel)
        }
    }
}