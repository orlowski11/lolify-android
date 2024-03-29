package com.example.lolify_android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import com.example.lolify_android.R

object AppFont {
    val Montserrat = FontFamily(
        Font(
            R.font.montserrat_medium,
            weight = FontWeight.Medium
        ),
        Font(
            R.font.montserrat_light,
            weight = FontWeight.Light
        ),
        Font(
            R.font.montserrat_extralight,
            weight = FontWeight.ExtraLight
        ),
        Font(
            R.font.montserrat_extralightitalic,
            weight = FontWeight.ExtraLight,
            style = FontStyle.Italic
        ),
        Font(
            R.font.montserrat_bold,
            weight = FontWeight.Bold
        )
    )
}

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = AppFont.Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = AppFont.Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    labelSmall = TextStyle(
        fontFamily = AppFont.Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)