package com.ojasx.eduplay.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ojasx.eduplay.R

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1500)

        navController.navigate("AuthGate") {
            popUpTo("SplashScreen") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
    }
}