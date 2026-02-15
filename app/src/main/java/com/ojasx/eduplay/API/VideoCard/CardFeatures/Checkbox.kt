package com.ojasx.eduplay.API.VideoCard.CardFeatures

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun CompletedCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF4CAF50),
                uncheckedColor = Color.LightGray,
                checkmarkColor = Color.White
            )
        )

        Text(
            text = "Done",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}