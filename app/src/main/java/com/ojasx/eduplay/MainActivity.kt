package com.ojasx.eduplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.google.android.gms.auth.api.identity.Identity
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.ui.Navigation.AppNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val oneTapClient = Identity.getSignInClient(applicationContext)
        val viewModel: PlaylistViewModel

        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}