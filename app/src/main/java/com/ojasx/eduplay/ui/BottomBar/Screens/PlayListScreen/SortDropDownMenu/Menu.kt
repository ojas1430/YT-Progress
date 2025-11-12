package com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.SortDropDownMenu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SortDropdownMenu(
    selectedSort : String,
    onSortSelected: (String)->Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var options = listOf("Default", "Completed Watching", "Revise", "Pinned")
    var selectedOption by remember { mutableStateOf(options[0]) }

    val purple = Color(0xFF7B4EFF)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier
                .height(42.dp)
                .width(160.dp),
            border = BorderStroke(1.dp, Color(0xFFFDB99B)),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0x22FFFFFF)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Sort By",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .border(
                    width = 2.dp,
                    color = purple,
                    shape = RoundedCornerShape(16.dp)
                )
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = Color(0x33000000),
                    spotColor = Color(0x33000000)
                )
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = option,
                                color = if (option == selectedOption) purple else Color(0xFF1A1A1A),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.2.sp,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            if (option == selectedOption) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = purple,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    },
                    onClick = {
                        selectedOption = option
                        expanded = false
                    }
                )

                // thin divider line between items
                if (index != options.lastIndex) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(purple.copy(alpha = 0.4f))
                    )
                }
            }
        }
    }
}
