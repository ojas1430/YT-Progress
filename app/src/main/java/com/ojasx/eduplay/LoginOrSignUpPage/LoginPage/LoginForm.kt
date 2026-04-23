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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ojasx.eduplay.ViewModel.AuthState
import com.ojasx.eduplay.ViewModel.AuthViewModel


@Composable
fun LoginForm(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsState(initial = AuthState.Loading)

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            navController.navigate("Home") {
                popUpTo("LoginScreen") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
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

        Text(
            text = "Password", style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold

        )
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 24.dp)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
        )
        TextButton(
            onClick = { authViewModel.sendPasswordReset(email) },
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(
                text = "Forgot password?",
                fontSize = 14.sp
            )
        }
        Spacer(Modifier.height(15.dp))
        Button(
            onClick = { authViewModel.login(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color(0xFF87CEEB)
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Login",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp

            )
        }

        if (authState is AuthState.Error) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = (authState as AuthState.Error).message,
                color = Color.Red,
                fontSize = 13.sp
            )
        }
        if (authState is AuthState.PasswordResetEmailSent) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = (authState as AuthState.PasswordResetEmailSent).message,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(15.dp))
        TextButton(
            onClick = {
                navController.navigate("SignUpScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)

        ) {
            Text(
                "Don't have an account? Sign Up",
                fontSize = 16.sp

            )
        }

    }

}