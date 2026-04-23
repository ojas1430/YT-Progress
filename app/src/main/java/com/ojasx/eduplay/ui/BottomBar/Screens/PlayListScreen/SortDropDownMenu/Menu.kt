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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDropdownMenu(
    selectedSort: String,
    onSortSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Default", "Completed", "Revise", "Pinned")

    val purple = Color(0xFF7B4EFF)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .padding(start = 4.dp)
    ) {
        OutlinedTextField(
            value = selectedSort,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = {
                Text(
                    "Sort",
                    fontSize = 11.sp
                )
            },
            textStyle = TextStyle(fontSize = 13.sp), // Smaller text font
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFFFDB99B),
                unfocusedIndicatorColor = Color(0xFFFDB99B),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                cursorColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier
                .menuAnchor()
                .background(purple.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                .widthIn(min = 120.dp, max = 140.dp)
                .heightIn(max = 70.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .widthIn(min = 100.dp, max = 140.dp)
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
                                FontWeight.SemiBold else FontWeight.Medium,
                            fontSize = 13.sp
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
                                tint = purple,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                )
            }
        }
    }
}