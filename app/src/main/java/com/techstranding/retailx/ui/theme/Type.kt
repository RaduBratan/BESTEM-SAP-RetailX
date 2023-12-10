package com.techstranding.retailx.ui.theme

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.techstranding.retailx.R

val plusJakartaSans = FontFamily(
    Font(R.font.plus_jakarta_sans_bold, FontWeight.Bold),
    Font(R.font.plus_jakarta_sans_semi_bold, FontWeight.SemiBold),
    Font(R.font.plus_jakarta_sans_medium, FontWeight.Medium),
    Font(R.font.plus_jakarta_sans_regular, FontWeight.Normal),
)

val noFontPadding = PlatformTextStyle(
    includeFontPadding = false,
)