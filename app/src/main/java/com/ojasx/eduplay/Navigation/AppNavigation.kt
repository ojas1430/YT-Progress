package com.ojasx.eduplay.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.BottomBar.BottomBar
import com.ojasx.eduplay.BottomBar.Screens.PlayListScreen.PlaylistScreen
import com.ojasx.eduplay.BottomBar.Screens.SettingsScreen.SettingsScreen
import com.ojasx.eduplay.HelpAndSupport.ReportABug.ReportABugMainScreen
import com.ojasx.eduplay.HelpAndSupport.ReportABug.ReportBugButton
import com.ojasx.eduplay.HomePage
import com.ojasx.eduplay.LoginOrSignUpPage.LoginPage.LoginPage
import com.ojasx.eduplay.LoginOrSignUpPage.SignUpPgae.SignUpPage
import com.ojasx.eduplay.MainScreen.MainScreen
import com.ojasx.eduplay.Notifications.LocalNotifications.ShowLocalNotificationScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val playlistviewModel : PlaylistViewModel = viewModel()
    var selectedSort by remember { mutableStateOf("Default") }
    val onSortSelected: (String) -> Unit = { newSort -> selectedSort = newSort }
    NavHost(
        navController = navController,
        startDestination ="BottomBar"  //"MainScreen"//
    ) {
        composable("BottomBar"){
            BottomBar(playlistviewModel,navController,selectedSort,onSortSelected)
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
            HomePage(navController,playlistviewModel,selectedSort,onSortSelected)
        }

        composable("PlaylistScreen"){
            PlaylistScreen(playlistviewModel,navController,selectedSort,onSortSelected)
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