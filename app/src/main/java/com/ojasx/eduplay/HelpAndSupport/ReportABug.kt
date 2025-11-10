package com.ojasx.eduplay.HelpAndSupport

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ReportBugButton() {
    val context = LocalContext.current

    Button(onClick = {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("support@EduPlay.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Bug Report - EduPlay App")
            putExtra(
                Intent.EXTRA_TEXT,
                """
                Please describe the bug below:
                
                Steps to reproduce:
                1. 
                2. 
                3. 
                
                Expected behavior:
                
                Actual behavior:
                
                Device Info:
                - Android Version: ${android.os.Build.VERSION.RELEASE}
                - Device: ${android.os.Build.MODEL}
                """.trimIndent()
            )
        }
        context.startActivity(Intent.createChooser(intent, "Send bug report"))
    }) {
        Text("Report a Bug")
    }
}
