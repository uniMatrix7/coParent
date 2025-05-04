package com.bignerdranch.android.coparent.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.coparent.R

private val TimesFamily = FontFamily(
    Font(R.font.times, weight = FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = TimesFamily,
        fontWeight = FontWeight.Bold,
        fontSize   = 32.sp
    ),
    titleMedium = TextStyle(
        fontFamily = TimesFamily,
        fontWeight = FontWeight.Normal,
        fontSize   = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = TimesFamily,
        fontSize   = 16.sp
    )
    // …add more styles if you like…
)

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */

