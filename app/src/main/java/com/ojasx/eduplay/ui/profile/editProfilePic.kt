
package com.ojasx.eduplay.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ojasx.eduplay.ViewModel.ProfileViewModel

@Composable
fun ProfileImagePicker(viewModel: ProfileViewModel) {

    val profileUri by viewModel.profileImage.observeAsState()
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val finalUri = tempUri ?: profileUri?.let { Uri.parse(it) }

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val profileSize = (screenWidth * 0.18).dp

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        tempUri = uri
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        // Image Circle
        Box(
            modifier = Modifier
                .size(profileSize)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (finalUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(finalUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(profileSize * 0.6f),
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Buttons Row
        Row {
            if (tempUri != null) {
                Button(
                    onClick = {
                        viewModel.updateProfileImage(tempUri.toString())
                        tempUri = null
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))
                ) { Text("Save") }

                Spacer(modifier = Modifier.width(10.dp))
            }

            if (profileUri != null) {
                Button(
                    onClick = {
                        viewModel.updateProfileImage(null)
                        tempUri = null
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFFD32F2F))
                ) { Text("Remove") }
            }
        }
        Text("Tap to change your profile photo",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
