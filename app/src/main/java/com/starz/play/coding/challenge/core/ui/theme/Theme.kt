package com.starz.play.coding.challenge.core.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    onPrimary = White,
    background = Black,
    onBackground = White,
    surface = Surface,
    onSurface = White,
    secondary = Gray,
    onSecondary = White,
    error = Error,
    onError = White
)

@Composable
fun STARZPLAYCodingChallengeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content,
    )
}
