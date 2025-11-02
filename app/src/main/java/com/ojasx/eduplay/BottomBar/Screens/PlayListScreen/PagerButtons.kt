package com.ojasx.eduplay.BottomBar.Screens.PlayListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ojasx.eduplay.ui.theme.bodycolor

@Composable
fun PagerButtons(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //  Previous
            PageButton("<", enabled = currentPage > 1) {
                if (currentPage > 1) onPageChange(currentPage - 1)
            }

            Spacer(Modifier.width(8.dp))

            // Number buttons (1 ... totalPages)
            val pagesToShow = buildList {
                add(1)
                if (currentPage > 3) add(-1) // -1 means ellipsis
                val start = maxOf(2, currentPage - 1)
                val end = minOf(totalPages - 1, currentPage + 1)
                for (i in start..end) add(i)
                if (currentPage < totalPages - 2) add(-1)
                if (totalPages > 1) add(totalPages)
            }

            pagesToShow.forEach { page ->
                Spacer(Modifier.width(4.dp))
                when (page) {
                    -1 -> Text("...", color = Color.White, fontSize = 16.sp)
                    else -> PageButton(
                        text = page.toString(),
                        selected = page == currentPage
                    ) { onPageChange(page) }
                }
            }

            Spacer(Modifier.width(8.dp))

            // ▶️ Next
            PageButton(">", enabled = currentPage < totalPages) {
                if (currentPage < totalPages) onPageChange(currentPage + 1)
            }
        }
    }
}

@Composable
fun PageButton(
    text: String,
    selected: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val bgColor = when {
        selected -> bodycolor
        else -> Color.Transparent
    }

    val borderColor = when {
        selected -> bodycolor
        else -> Color.White
    }

    Box(
        modifier = Modifier
            .size(30.dp)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .background(bgColor, RoundedCornerShape(8.dp))
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) Color.White else Color.White,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 15.sp
        )
    }
}
