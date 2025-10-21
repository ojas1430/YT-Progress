package com.ojasx.eduplay.MainScreen

import android.R.attr.bottom
import android.util.Log
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.ojasx.eduplay.R


@Composable
fun MainScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        DottedBackground()
        ColumnArrangements()
        MainScreenButton(navController)
        AnimationArrangement()
    }
}

@Composable
fun ColumnArrangements() {
    Column (
        modifier= Modifier.fillMaxWidth(),

        ){
        Heading()
        CardInfo()
    }
}

@Composable
fun AnimationArrangement(){
    Box(
        modifier= Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier.align(Alignment.BottomEnd)
        ){
            LottieAnimationButton(
                onClick = {}
            )
        }
    }
}

@Composable
fun MainScreenButton(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier.align(Alignment.BottomCenter)){

    Button(
        onClick = {
            navController.navigate("LoginScreen")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color(0xFFBA55D3)
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = "Lets Start",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
            ,modifier = Modifier.padding(bottom = 33.dp)

        )
    }

}
    }
}