package com.ojasx.eduplay.ui.profile

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ojasx.eduplay.ViewModel.ProfileViewModel
import com.ojasx.eduplay.R

@Composable
fun displayProfilePic(profileViewModel: ProfileViewModel) {
    val profileUri by profileViewModel.profileImage.observeAsState()
    val finalUri = profileUri?.let { Uri.parse(it) }
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val profileSize = (screenWidth * 0.18).dp
    Image(
        painter = if (finalUri != null)
            rememberAsyncImagePainter(finalUri)
        else
            rememberAsyncImagePainter(model = R.drawable.user),
        contentDescription = "Profile Image",
        modifier = Modifier
            .size(profileSize)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}