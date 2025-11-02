package com.ojasx.eduplay.BottomBar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.BottomBar.Screens.HomeScreen
import com.ojasx.eduplay.BottomBar.Screens.PlayListScreen.PlaylistScreen
import com.ojasx.eduplay.BottomBar.Screens.SettingsScreen
import com.ojasx.eduplay.StatusBar

@Composable
fun BottomBar(
    playlistViewModel: PlaylistViewModel,
    navController: NavController
) {
    StatusBar()

    val navItemList = listOf(
        NavItem("Home",Icons.Default.Home),
        NavItem("Playlist Screen",Icons.Default.PlayArrow),
        NavItem("Settings",Icons.Default.Settings)
    )
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )

                }
            }
        }
        ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            playlistViewModel = playlistViewModel,
            selectedIndex = selectedIndex
        )
    }
}

@Composable
fun ContentScreen(modifier: Modifier,
                  navController: NavController,
                  playlistViewModel: PlaylistViewModel,
                  selectedIndex : Int = 0,

) {
        when(selectedIndex){
            0 -> HomeScreen(navController,playlistViewModel)
            1 -> PlaylistScreen(playlistViewModel,navController)
            2 -> SettingsScreen(navController)
        }
}