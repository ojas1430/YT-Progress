
package com.ojasx.eduplay.ui.Navigation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.ui.BottomBar.BottomBar
import com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.PlaylistScreen
import com.ojasx.eduplay.ui.BottomBar.Screens.SettingsScreen.SettingsScreen
//import com.ojasx.eduplay.ui.HelpAndSupport.ReportABug.ReportABugMainScreen
//import com.ojasx.eduplay.ui.HelpAndSupport.ReportABug.ReportBugButton
import com.ojasx.eduplay.ui.HomePage
import com.ojasx.eduplay.LoginOrSignUpPage.LoginPage.LoginPage
import com.ojasx.eduplay.LoginOrSignUpPage.SignUpPgae.SignUpPage
import com.ojasx.eduplay.ui.MainScreen.MainScreen
import com.ojasx.eduplay.Notifications.LocalNotifications.ShowLocalNotificationScreen
import com.ojasx.eduplay.ViewModel.AuthState
import com.ojasx.eduplay.ViewModel.AuthViewModel
import com.ojasx.eduplay.ViewModel.ProfileViewModel
import com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.Stats.StatsButton
import com.ojasx.eduplay.ui.Player.YouTubePlayerScreen
import com.ojasx.eduplay.ui.Reusables.AdaptiveSystemBars
import com.ojasx.eduplay.ui.Reusables.FeatureComingSoonScreen
import com.ojasx.eduplay.ui.SplashScreen
import com.ojasx.eduplay.ui.helpAndSupport.faq.FAQScreen
import com.ojasx.eduplay.ui.profile.UserProfileScreen

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val playlistviewModel : PlaylistViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val isInternetAvailable by rememberInternetAvailability(context)
    var showNoInternetDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isInternetAvailable) {
        if (!isInternetAvailable) {
            showNoInternetDialog = true
        }
    }

    AdaptiveSystemBars(
        statusBarColor = MaterialTheme.colorScheme.background,
        navigationBarColor = MaterialTheme.colorScheme.surface
    )

    if (showNoInternetDialog && !isInternetAvailable) {
        AlertDialog(
            onDismissRequest = { showNoInternetDialog = false },
            title = {
                Text(
                    text = "No Internet Connection",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Please connect to the internet and try again.",
                    color = Color.Black.copy(alpha = 0.85f)
                )
            },
            confirmButton = {
                Button(
                    onClick = { showNoInternetDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("OK")
                }
            },
            containerColor = Color.White
        )
    }

    NavHost(
        navController = navController,
        startDestination ="SplashScreen"
    ) {

        composable("player/{videoId}") { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            YouTubePlayerScreen(videoId = videoId)
        }

        composable("AuthGate") {
            val authState by authViewModel.authState.collectAsState(initial = AuthState.Loading)
            LaunchedEffect(authState) {
                when (authState) {
                    is AuthState.Authenticated -> {
                        navController.navigate("BottomBar") {
                            popUpTo("AuthGate") { inclusive = true }
                        }
                    }
                    is AuthState.Unauthenticated -> {
                        navController.navigate("LoginScreen") {
                            popUpTo("AuthGate") { inclusive = true }
                        }
                    }
                    is AuthState.EmailNotVerified,
                    is AuthState.EmailVerificationSent -> {
                        navController.navigate("VerifyEmailScreen") {
                            popUpTo("AuthGate") { inclusive = true }
                        }
                    }
                    else -> Unit
                }
            }
        }
        composable("VerifyEmailScreen") {
            val authState by authViewModel.authState.collectAsState(initial = AuthState.Loading)
            val padding = WindowInsets.systemBars.union(WindowInsets.ime).asPaddingValues()

            LaunchedEffect(authState) {
                when (authState) {
                    is AuthState.Authenticated -> {
                        navController.navigate("BottomBar") {
                            popUpTo("VerifyEmailScreen") { inclusive = true }
                        }
                    }
                    is AuthState.Unauthenticated -> {
                        navController.navigate("LoginScreen") {
                            popUpTo("VerifyEmailScreen") { inclusive = true }
                        }
                    }
                    else -> Unit
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(padding)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Verify your email",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                val message = when (authState) {
                    is AuthState.EmailVerificationSent -> (authState as AuthState.EmailVerificationSent).message
                    is AuthState.EmailNotVerified -> (authState as AuthState.EmailNotVerified).message
                    is AuthState.Error -> (authState as AuthState.Error).message
                    else -> "We sent you a verification link. Open your email and click the link."
                }

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black.copy(alpha = 0.8f)
                )

                Button(
                    onClick = { authViewModel.checkEmailVerification() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color(0xFF87CEEB)
                    )
                ) {
                    Text("I've verified, continue", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { authViewModel.resendEmailVerification() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Resend verification email")
                }

                OutlinedButton(
                    onClick = {
                        authViewModel.signOut()
                        navController.navigate("LoginScreen") {
                            popUpTo("VerifyEmailScreen") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Login")
                }

                Spacer(modifier = Modifier.size(8.dp))
            }
        }
        composable("BottomBar"){
            BottomBar(playlistviewModel,profileViewModel,authViewModel,navController)
        }
        composable("MainScreen") {
            MainScreen(navController)
        }
        composable("LoginScreen") {
            LoginPage(navController, authViewModel)
        }
        composable("SignUpScreen") {
            SignUpPage(navController, authViewModel)
        }
        composable("Home") {
            HomePage(navController,playlistviewModel,profileViewModel,authViewModel)
        }

        composable("PlaylistScreen"){
            PlaylistScreen(playlistviewModel,navController)
        }
        composable("SettingsScreen"){
            SettingsScreen(navController,profileViewModel,authViewModel)
        }
        composable("ShowLocalNotificationScreen"){
            ShowLocalNotificationScreen(navController)
        }
//        composable("ReportABugMainScreen"){
//            ReportABugMainScreen(navController)
//        }
//        composable("ReportBugButton"){
//            ReportBugButton()
//        }
        composable("UserProfileScreen"){
            UserProfileScreen(navController, profileViewModel, authViewModel)
        }
        composable("FeatureComingSoonScreen"){
            FeatureComingSoonScreen(navController)
        }
        composable("FAQScreen"){
            FAQScreen(navController)
        }
        composable("SplashScreen"){
            SplashScreen(navController)
        }
        composable("StatsButton"){
            StatsButton(playlistviewModel,navController)
        }
    }
}

@Composable
private fun rememberInternetAvailability(context: Context) = produceState(
    initialValue = isInternetAvailable(context),
    key1 = context
) {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            value = true
        }

        override fun onLost(network: Network) {
            value = isInternetAvailable(context)
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            value = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }

    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
    connectivityManager.registerNetworkCallback(request, callback)

    awaitDispose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

private fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
