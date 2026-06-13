package com.example.taskflow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    background = SurfaceBaseLight,
    surface = CardWhite,
    primary = BlueGradientStart,
    primaryContainer = BlueGradientEnd
)

private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    primary = Color(0xFF3B82F6),
    primaryContainer = Color(0xFF1D4ED8),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun TaskFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = TaskFlowTypography,
        content = content
    )
}