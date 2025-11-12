package com.ojasx.eduplay.ui.BottomBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.ui.BottomBar.Screens.HomeScreen
import com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.PlaylistScreen
import com.ojasx.eduplay.ui.BottomBar.Screens.SettingsScreen.SettingsScreen
import com.ojasx.eduplay.Data.Local.BottomBar.NavItem
import com.ojasx.eduplay.ui.Reusables.StatusBar

@Composable
fun BottomBar(
    playlistViewModel: PlaylistViewModel,
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
        screenHeightDp < 700 -> 56.dp
        screenHeightDp < 900 -> 64.dp
        else -> 72.dp
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Surface(
                color = Color(0xFFF8F8F8),
                tonalElevation = 2.dp,
                modifier = Modifier
                    .height(bottomBarHeight)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItemList.forEachIndexed { index, navItem ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { selectedIndex = index }
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = navItem.label,
                                tint = if (selectedIndex == index)
                                    Color(0xFF7B4EFF)
                                else
                                    Color(0xFF999999),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = navItem.label,
                                color = if (selectedIndex == index)
                                    Color(0xFF7B4EFF)
                                else
                                    Color(0xFF999999),
                                style = MaterialTheme.typography.labelSmall
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
            selectedIndex = selectedIndex,
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier,
    navController: NavController,
    playlistViewModel: PlaylistViewModel,
    selectedIndex: Int = 0,
) {
    when (selectedIndex) {
        0 -> HomeScreen(navController, playlistViewModel)
        1 -> PlaylistScreen(playlistViewModel, navController)
        2 -> SettingsScreen(navController)
    }
}
