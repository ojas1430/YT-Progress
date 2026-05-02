package com.ojasx.eduplay.FirebaseSignin

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.ojasx.eduplay.R
import com.ojasx.eduplay.ViewModel.AuthViewModel

@Composable
fun GoogleButton(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current


    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("927162964914-ae3ldh7jmg4bu4526jdnst4133qmpekk.apps.googleusercontent.com")
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken != null) {
                authViewModel.firebaseAuthWithGoogle(idToken)
                navController.navigate("Home")
            } else {
                Toast.makeText(context, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 25.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Continue with Google",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                IconButton(
                    onClick = {
                        googleSignInClient.signOut()
                        authViewModel.setLoading()


                        launcher.launch(googleSignInClient.signInIntent)
                    },
                    modifier = Modifier.size(45.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.googlesymbol),
                        contentDescription = null
                    )
                }
            }
        }
    }
}