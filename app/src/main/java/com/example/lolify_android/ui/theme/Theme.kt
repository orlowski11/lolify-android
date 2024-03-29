package com.example.lolify_android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrim,
    secondary = DarkSec,
    tertiary = DarkTert,
    onPrimaryContainer = DarkTextFieldBackground,
    error = Error
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrim,
    secondary = LightSec,
    tertiary = LightTert,
    onPrimaryContainer = LightTextFieldBackground,
    error = Error

    /* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/
)

@Composable
fun LolifyandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current

    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setSystemBarsColor(color = colorScheme.primary, darkIcons = false)
        systemUiController.setNavigationBarColor(color = colorScheme.secondary, darkIcons = false)
    } else {
        systemUiController.setSystemBarsColor(color = colorScheme.primary, darkIcons = true)
        systemUiController.setNavigationBarColor(color = colorScheme.secondary, darkIcons = true)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides CustomRipple,
            content = content
        )
    }
}

private object CustomRipple : RippleTheme {
    @Composable
    override fun defaultColor() = MaterialTheme.colorScheme.primary

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        MaterialTheme.colorScheme.primary,
        lightTheme = !isSystemInDarkTheme()
    )
}