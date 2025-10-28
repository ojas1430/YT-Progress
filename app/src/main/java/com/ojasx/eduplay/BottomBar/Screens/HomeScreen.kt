package com.ojasx.eduplay.BottomBar.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.DialogNavigator
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.StatusBar
import com.ojasx.eduplay.linktextfield.LinkInputScreen

@Composable
fun HomeScreen(
    navController: NavController,
    playlistviewmodel: PlaylistViewModel
) {
    StatusBar()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECEFFF))
            .statusBarsPadding()
    ) {
        SoftBlurBackground()
        Column {
            Text("Hello from HomeScreen!", color = Color.Black)
            LinkInputScreen(playlistviewmodel)
        }
    }
}