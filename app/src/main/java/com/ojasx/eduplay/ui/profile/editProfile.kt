package com.ojasx.eduplay.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ojasx.eduplay.ViewModel.ProfileViewModel
import com.ojasx.eduplay.ui.theme.cardcolor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    navController: NavController,
    profileviewModel: ProfileViewModel
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "User profile",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        profileviewModel.updateProfile(firstName,lastName,email)
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Save",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardcolor
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile icon
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, CircleShape)
                    .padding(16.dp),
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // First name
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First name", style = MaterialTheme.typography.bodySmall) },
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = cardcolor,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = cardcolor,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = cardcolor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Last name
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last name", style = MaterialTheme.typography.bodySmall) },
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = cardcolor,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = cardcolor,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = cardcolor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", style = MaterialTheme.typography.bodySmall) },
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = cardcolor,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = cardcolor,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = cardcolor
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Logout button
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("LOGOUT", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Delete all user data
            TextButton(onClick = {  }) {
                Text(
                    "DELETE ALL USER DATA",
                    color = Color(0xFFE53935),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
