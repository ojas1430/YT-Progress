
package com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.SortDropDownMenu

import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDropdownMenu(
    selectedSort: String,
    onSortSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Default", "Completed Watching", "Revise", "Pinned")

    val purple = Color(0xFF7B4EFF)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        OutlinedTextField(
            value = selectedSort,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text("Sort By") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },

            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = purple,
                unfocusedIndicatorColor = Color(0xFFFDB99B),
                focusedTextColor = purple,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = purple.copy(0.7f),
                cursorColor = purple
            ),

            modifier = Modifier
                .menuAnchor()
                .background(purple, RoundedCornerShape(8.dp))
                .width(180.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(180.dp)
                .shadow(12.dp, RoundedCornerShape(12.dp))
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = if (option == selectedSort) purple else Color.Black,
                            fontWeight = if (option == selectedSort)
                                FontWeight.SemiBold else FontWeight.Medium
                        )
                    },
                    onClick = {
                        onSortSelected(option)
                        expanded = false
                    },
                    trailingIcon = {
                        if (option == selectedSort) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = purple
                            )
                        }
                    }
                )
            }
        }
    }
}