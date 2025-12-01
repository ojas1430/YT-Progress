package com.ojasx.eduplay.ui.helpAndSupport.faq

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ojasx.eduplay.ui.Reusables.ReusableTopBar

@Composable
fun FAQScreen(navController: NavController) {

    val faqs = listOf(
        "How do I add a YouTube playlist?" to
                "Paste the playlist link and EduPlay automatically loads all videos with progress tracking.",

        "Can I mark videos as completed?" to
                "Yes, each video has actions like Done, Revise, Notes, and Watch Later.",

        "Does EduPlay save my progress?" to
                "All progress is stored locally on your device. Cloud sync is coming soon.",

        "Can I take notes for videos?" to
                "Yes, you can add timestamps and notes for each video individually.",

        "Do I need internet always?" to
                "Only when loading playlists. Progress & notes work completely offline.",

        "What upcoming features to expect?" to
                "Advanced statistics, reminders, daily goals, and AI-powered revision."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFF8F9FF),
                        Color(0xFFFFFFFF)
                    )
                )
            )
    ) {


        ReusableTopBar(
            "FAQ's",
            navController
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Common Questions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            faqs.forEach { (question, answer) ->
                FAQItem(question, answer)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    question,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }

            AnimatedVisibility(
                expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = answer,
                    fontSize = 14.sp,
                    color = Color(0xFF606060),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
