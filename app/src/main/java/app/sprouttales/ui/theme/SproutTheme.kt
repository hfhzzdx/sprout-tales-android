package app.sprouttales.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SproutLightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFF4A90E2),      // 蓝色主色
    onPrimary = Color.White,
    secondary = Color(0xFF7EC8FF),    // 浅蓝
    onSecondary = Color(0xFF003355),
    tertiary = Color(0xFF64B5F6),
    background = Color(0xFFF2F8FF),   // 淡蓝背景
    onBackground = Color(0xFF1B2B3A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B2B3A),
    error = Color(0xFFB00020),
    onError = Color.White
)

@Composable
fun SproutTheme(content: @Composable () -> Unit) {
    val colors = SproutLightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}
