package com.ojasx.eduplay.MainScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.ojasx.eduplay.R

@Composable
fun DottedBackground(
    dotColor: Color = Color.White,
    dotRadius: Float = 2f,
    spacing: Float = 20f,
    backgroundColor: Color = Color.Black
) {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
        .padding(20.dp)
        .clip(RoundedCornerShape(45.dp))
    ) {
        val width = size.width
        val height = size.height

        for (x in 0..(width / spacing).toInt()) {
            for (y in 0..(height / spacing).toInt()) {
                drawCircle(
                    color = dotColor.copy(alpha = 0.2f), // subtle like in the screenshot
                    radius = dotRadius,
                    center = Offset(x * spacing, y * spacing)
                )
            }
        }
    }
}