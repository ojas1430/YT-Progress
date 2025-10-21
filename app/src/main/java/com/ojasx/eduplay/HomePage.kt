package com.ojasx.eduplay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ojasx.eduplay.BottomBar.BottomBar

@Composable
fun HomePage(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        BottomBar()
    }
}