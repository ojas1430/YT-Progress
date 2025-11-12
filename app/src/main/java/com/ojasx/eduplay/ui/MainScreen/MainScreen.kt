package com.ojasx.eduplay.ui.MainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController


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