package com.example.taskflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.taskflow.R
import com.example.taskflow.Screen

sealed class CustomNavItem(val route: String, val icon: ImageVector, val englishLabel: String) {
    object Home : CustomNavItem(Screen.Dashboard.route, Icons.Default.Home, "HOME")
    object Add : CustomNavItem(Screen.AddTask.route, Icons.Default.AddCircle, "ADD")
    object Progress : CustomNavItem(Screen.Progress.route, Icons.Default.Refresh, "PROGRESS")
    object Settings : CustomNavItem(Screen.Settings.route, Icons.Default.Settings, "SETTINGS")
}

@Composable
fun TaskFlowBottomBar(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val locale = context.resources.configuration.locales[0]
    val isIndonesian = locale.language == "in" || locale.language == "id"

    val items = listOf(
        CustomNavItem.Home,
        CustomNavItem.Add,
        CustomNavItem.Progress,
        CustomNavItem.Settings
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            val activeColor = MaterialTheme.colorScheme.primary
            val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            val capsuleBgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)

            val dynamicLabel = when (item) {
                CustomNavItem.Home -> if (isIndonesian) "BERANDA" else item.englishLabel
                CustomNavItem.Add -> if (isIndonesian) "TAMBAH" else item.englishLabel
                CustomNavItem.Progress -> if (isIndonesian) "PROGRES" else item.englishLabel
                CustomNavItem.Settings -> if (isIndonesian) "PENGATURAN" else item.englishLabel
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .height(38.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(capsuleBgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = dynamicLabel,
                            tint = activeColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dynamicLabel,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = activeColor,
                        letterSpacing = 0.5.sp
                    )
                } else {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = dynamicLabel,
                        tint = inactiveColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dynamicLabel,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = inactiveColor,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}