package com.ojasx.eduplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.ojasx.eduplay.API.PlaylistScreen
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.LoginOrSignUpPage.LoginPage.LoginPage
import com.ojasx.eduplay.Navigation.AppNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val oneTapClient = Identity.getSignInClient(applicationContext)
        val viewModel: PlaylistViewModel

        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
            //    AppNavigation()
                PlaylistScreen()
            }
        }
    }
}