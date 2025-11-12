package com.ojasx.eduplay.LoginOrSignUpPage.SignUpPgae

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ojasx.eduplay.ViewModel.AuthState
import com.ojasx.eduplay.ViewModel.AuthViewModel

@Composable
fun SignUpForm(navController: NavController,authViewModel: AuthViewModel) {

    var name by remember { mutableStateOf("")  }
    var email by remember { mutableStateOf("")  }
    var password by remember { mutableStateOf("")  }
    var confirmPassword by remember { mutableStateOf("")  }

    val _authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    // if already logged in , directly go to home page
    LaunchedEffect(_authState.value) {
        when (_authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate("Home")
            }
            is AuthState.Error ->
                Toast.makeText(context,
                    (_authState.value as AuthState.Error).message ,
                    Toast.LENGTH_SHORT).show()
            else -> Unit
        }

    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ){
        //Name

        Text(
            text = "Name", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
        )
        BasicTextField(
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                Color.Black,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 16.dp)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Gray,
                        start = _root_ide_package_.androidx.compose.ui.geometry.Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth

                    )
                }
        )

        //email
        Text(
            text = "Email", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
        )
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                Color.Black,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 16.dp)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Gray,
                        start = _root_ide_package_.androidx.compose.ui.geometry.Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth

                    )
                }
        )

        //password
        Text(
            text = "PassWord", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
        )
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                Color.Black,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 16.dp)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Gray,
                        start = _root_ide_package_.androidx.compose.ui.geometry.Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth

                    )
                }
        )

        //Confirm Password
        Text(
            text = "Confirm Password", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
        )
        BasicTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                Color.Black,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 16.dp)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Gray,
                        start = _root_ide_package_.androidx.compose.ui.geometry.Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth

                    )
                }
        )

        Spacer(Modifier.height(15.dp))
        Button(
            onClick = {
                authViewModel.SignUp(name,email,password,confirmPassword)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color(0xFF87CEEB)
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Create Account",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp

            )
        }

        Spacer(Modifier.height(15.dp))
        TextButton(
            onClick = {
                navController.navigate("LoginScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)

        ) {
            Text(
                "Already have an account? Sign In",
                fontSize = 16.sp

            )
        }


    }

}