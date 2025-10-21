package com.ojasx.eduplay.LoginOrSignUpPage.SignUpPgae

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ojasx.eduplay.AuthViewModel
import com.ojasx.eduplay.FirebaseSignin.GoogleButton
import com.ojasx.eduplay.LoginOrSignUpPage.LoginPage.LoginPic
import com.ojasx.eduplay.R


@Composable
fun SignUpPage(navController: NavController) {
    val authViewModel: AuthViewModel = viewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top black background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .background(Color.Black)
        ){
            Image(painter = painterResource(
                id = R.drawable.adduser),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp))
        }

        // White foreground with curved top-left corner
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp)
                .background(
                    color = Color.White,
                )
        ) {
            // Place your Login UI here if needed
            Column(modifier = Modifier.fillMaxSize()
            ) {
                SignUpPic()
                SignUpForm(navController = navController, authViewModel = authViewModel)
                GoogleButton(navController)
            }
        }
    }
}
