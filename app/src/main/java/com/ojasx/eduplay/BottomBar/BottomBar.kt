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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.play.integrity.internal.s
import com.ojasx.eduplay.BottomBar.Screens.HomeScreen
import com.ojasx.eduplay.BottomBar.Screens.PlaylistScreen
import com.ojasx.eduplay.BottomBar.Screens.SettingsScreen
import com.ojasx.eduplay.R

@Preview
@Composable
fun BottomBar() {

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
        ContentScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex : Int = 0) {
        when(selectedIndex){
            0 -> HomeScreen()
            1 -> PlaylistScreen()
            2 -> SettingsScreen()
        }
}