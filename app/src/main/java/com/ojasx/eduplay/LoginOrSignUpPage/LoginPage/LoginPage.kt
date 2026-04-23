package com.ojasx.eduplay.LoginOrSignUpPage.LoginPage

import LoginForm
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ojasx.eduplay.FirebaseSignin.GoogleButton
import com.ojasx.eduplay.R
import com.ojasx.eduplay.ViewModel.AuthViewModel

@Composable
fun LoginPage(navController: NavController, authViewModel: AuthViewModel) {
    val contentPadding = WindowInsets.systemBars
        .union(WindowInsets.ime)
        .asPaddingValues()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Short header (no fixed screen-percentage height)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black, RoundedCornerShape(18.dp))
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.adduser),
                    contentDescription = null,
                    modifier = Modifier.size(52.dp)
                )
            }
        }

        // Main content (scrolls on small screens)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(18.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LoginPic()
                LoginForm(navController, authViewModel)
            }
        }

        // Google sign-in (wrap content; visible + scrollable)
        item {
            GoogleButton(navController)
        }
    }
}