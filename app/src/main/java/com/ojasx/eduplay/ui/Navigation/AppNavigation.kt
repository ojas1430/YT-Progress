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

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val playlistviewModel : PlaylistViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination ="BottomBar"  //"MainScreen"//
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
        composable("ShowLocalNotificationScreen"){
            ShowLocalNotificationScreen(navController)
        }
        composable("ReportABugMainScreen"){
            ReportABugMainScreen(navController)
        }
        composable("ReportBugButton"){
            ReportBugButton()
        }
    }
}