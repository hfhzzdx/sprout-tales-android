package app.sprouttales.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SproutLightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50),      // 柔和绿色
    onPrimary = Color.White,
    secondary = Color(0xFFFFC107),    // 暖黄色
    onSecondary = Color(0xFF3E2723),
    tertiary = Color(0xFF8BC34A),
    background = Color(0xFFFFFBF0),   // 奶油底
    onBackground = Color(0xFF2E2A24),
    surface = Color(0xFFFFF6D9),      // 卡片淡黄
    onSurface = Color(0xFF2E2A24),
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
