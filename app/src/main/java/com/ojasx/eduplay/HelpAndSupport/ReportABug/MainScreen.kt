package com.ojasx.eduplay.HelpAndSupport.ReportABug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ojasx.eduplay.ReusableCard
import com.ojasx.eduplay.ReusableTopBar
import com.ojasx.eduplay.ui.theme.cardcolor

@Composable
fun ReportABugMainScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    cardcolor.copy(alpha = 0.2f),
                    Color(0xFFF7F7FF)
                )
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ReusableTopBar(
                title = "Report A Bug",
                navController
            )
            Spacer(modifier = Modifier.height(16.dp))

            ReusableCard(
                title = "Something Went Wrong?",
                description = "Tell us what went wrong so we can fix it as soon as possible.",
                "Report",
                onClick = {navController.navigate("ReportBugButton")},
                navController
            )
        }
    }
}