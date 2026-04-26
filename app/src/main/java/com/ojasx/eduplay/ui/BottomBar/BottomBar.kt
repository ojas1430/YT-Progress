package com.ojasx.eduplay.ui.BottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.ui.BottomBar.Screens.HomeScreen
import com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.PlaylistScreen
import com.ojasx.eduplay.ui.BottomBar.Screens.SettingsScreen.SettingsScreen
import com.ojasx.eduplay.ViewModel.ProfileViewModel
import com.ojasx.eduplay.ViewModel.AuthViewModel
import com.ojasx.eduplay.ui.Reusables.StatusBar

@Composable
fun BottomBar(
    playlistViewModel: PlaylistViewModel,
    profileViewModel: ProfileViewModel,
    authViewModel: AuthViewModel,
    navController: NavController,
) {
    StatusBar()

    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Playlist", Icons.Default.PlayArrow),
        NavItem("Settings", Icons.Default.Settings)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }


    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp
    val bottomBarHeight = when {
        screenHeightDp < 700 -> 70.dp
        screenHeightDp < 900 -> 78.dp
        else -> 84.dp
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 18.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(bottomBarHeight)
                        .shadow(
                            elevation = 18.dp,
                            shape = RoundedCornerShape(28.dp),
                            ambientColor = Color(0xFF5130C7).copy(alpha = 0.25f),
                            spotColor = Color.Black.copy(alpha = 0.22f)
                        )
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF111427),
                                    Color(0xFF1E2447),
                                    Color(0xFF2D3569)
                                )
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItemList.forEachIndexed { index, navItem ->
                        val isSelected = selectedIndex == index
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(22.dp))
                                .background(
                                    if (isSelected) Color.White.copy(alpha = 0.18f)
                                    else Color.Transparent
                                )
                                .clickable { selectedIndex = index }
                                .padding(vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = navItem.label,
                                tint = if (isSelected)
                                    Color.White
                                else
                                    Color.White.copy(alpha = 0.68f),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = navItem.label,
                                color = if (isSelected)
                                    Color.White
                                else
                                    Color.White.copy(alpha = 0.68f),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navController = navController,
            playlistViewModel = playlistViewModel,
            profileViewModel = profileViewModel,
            authViewModel = authViewModel,
            selectedIndex = selectedIndex,
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier,
    navController: NavController,
    playlistViewModel: PlaylistViewModel,
    profileViewModel: ProfileViewModel,
    authViewModel: AuthViewModel,
    selectedIndex: Int = 0,
) {
    when (selectedIndex) {
        0 -> HomeScreen(navController, playlistViewModel)
        1 -> PlaylistScreen(playlistViewModel, navController)
        2 -> SettingsScreen(navController,profileViewModel, authViewModel)
    }
}


data class NavItem(
    val label: String,
    val icon: ImageVector
)