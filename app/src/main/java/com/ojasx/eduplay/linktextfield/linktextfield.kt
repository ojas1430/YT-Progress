package com.ojasx.eduplay.linktextfield

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun LinkInputScreen() {
    val context = LocalContext.current
    var link by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // TextField for entering the link
        OutlinedTextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("Enter your link") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to handle click
        Button(
            onClick = {
                if (link.text.isNotEmpty()) {
                    Toast.makeText(context, "Link: ${link.text}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter a link", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
