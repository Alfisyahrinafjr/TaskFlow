package com.example.taskflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.taskflow.ui.components.EditorialPrimaryButton
import com.example.taskflow.ui.theme.*

@Composable
fun TaskFlowOnboardingScreen(onOnboardingComplete: () -> Unit) {
    var currentPage by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBaseLight)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onOnboardingComplete) {
                Text("Skip", color = TextSecondaryMuted)
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        when (currentPage) {
            1 -> {
                Box(modifier = Modifier.size(120.dp).background(Color.White, CircleShape), contentAlignment = Alignment.Center) {
                    Text("🔔", style = MaterialTheme.typography.displayLarge)
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text("Strategize with\nPrecision", style = MaterialTheme.typography.displayLarge, color = TextPrimaryDark, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Categorize your missions into Work, Personal, or College. Set deadlines and let our architectural reminders keep you on track.", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted, textAlign = TextAlign.Center)
            }
            2 -> {
                Text("MODULE 01", style = MaterialTheme.typography.bodySmall, color = Color(0xFF1E4CE5))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Master the\nBREAD Loop.", style = MaterialTheme.typography.displayLarge, color = TextPrimaryDark, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Productivity isn't a chore; it's a rhythm. Manage your lifecycle through professional-grade architectural controls.", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted, textAlign = TextAlign.Center)
            }
            3 -> {
                Box(modifier = Modifier.size(120.dp).background(Color.White, CircleShape), contentAlignment = Alignment.Center) {
                    Text("75%", style = MaterialTheme.typography.titleMedium, color = Color(0xFF1E4CE5))
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text("Visualize Your\nImpact", style = MaterialTheme.typography.displayLarge, color = TextPrimaryDark, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Track your efficiency with data-driven progress charts. See your weekly impact and optimize your editorial workflow.", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted, textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 24.dp)) {
            repeat(3) { index ->
                val isSelected = (index + 1) == currentPage
                Box(modifier = Modifier.size(width = if (isSelected) 24.dp else 8.dp, height = 8.dp).background(color = if (isSelected) Color(0xFF1E4CE5) else Color.LightGray, shape = CircleShape))
            }
        }

        EditorialPrimaryButton(
            text = if (currentPage == 3) "Get Started ➔" else "Next Chapter",
            onClick = { if (currentPage < 3) currentPage += 1 else onOnboardingComplete() }
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}