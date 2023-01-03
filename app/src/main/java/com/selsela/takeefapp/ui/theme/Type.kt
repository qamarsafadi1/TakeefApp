package com.selsela.takeefapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val sloganStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    color = TextColor
)

val textMeduim = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
)
val textTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = Color.White
)
val textBodyStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    color = Color.White.copy(alpha = 0.65f),
    lineHeight = 25.sp,
    letterSpacing = 0.5.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.Both
    )
)
val text14 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    color = TextColor
)
val text18 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = TextColor
)
val text12 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp)
val text20 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp)
val buttonText  = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 13.sp,
    color = Color.White
)