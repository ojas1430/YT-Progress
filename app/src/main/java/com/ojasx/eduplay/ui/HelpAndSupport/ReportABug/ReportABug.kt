package com.ojasx.eduplay.ui.HelpAndSupport.ReportABug

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ReportBugButton() {
    val context = LocalContext.current

    Button(onClick = {
        val deviceInfo = """
            Device Info:
            - Android Version: ${Build.VERSION.RELEASE}
            - Device: ${Build.MODEL}
        """.trimIndent()

        val emailBody = """
            Please describe the bug below:
            
            Steps to reproduce:
            1.
            2.
            3.
            
            Expected behavior:
            
            Actual behavior:
            
            $deviceInfo
        """.trimIndent()

        // Use mailto: URI which is more reliable for opening email apps
        val mailtoUri = Uri.parse("mailto:support@EduPlay.com").buildUpon()
            .appendQueryParameter("subject", "Bug Report - EduPlay App")
            .appendQueryParameter("body", emailBody)
            .build()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = mailtoUri
        }

        try {
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // Fallback to ACTION_SEND if no email app is available
                val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "message/rfc822"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("support@EduPlay.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "Bug Report - EduPlay App")
                    putExtra(Intent.EXTRA_TEXT, emailBody)
                }
                context.startActivity(
                    Intent.createChooser(fallbackIntent, "Send bug report via")
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }) {
        Text("Report a Bug")
    }
}