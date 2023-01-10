package com.selsela.takeefapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
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
    color = TextColor,
    lineHeight = 25.sp,
    letterSpacing = 0.5.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.Both
    )
)
val text14White = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    color = Color.White,
    lineHeight = 0.sp,
    letterSpacing = 0.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.Both
    ),
    textAlign = TextAlign.Start
)
val text14WhiteCenter = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    color = Color.White,
    lineHeight = 0.sp,
    letterSpacing = 0.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    ),
)
val text14Meduim = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    color = TextColor
)
val text18 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = TextColor
)
val text11 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 11.sp,
    color = Color.White.copy(0.85f)
)
val text13 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 13.sp,
)
val text11Meduim = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    color = Color.White.copy(0.85f)
)
val text18Meduim = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    color = SecondaryColor
)
val text18Book = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    color = TextColor
)
val text12 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.5.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.Both
    )
)
val text16Medium = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)
val text16 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
)
val text12Meduim = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp
)

val text20 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp
)
val text21 = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 21.sp
)
val text20Meduim = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp
)
val text20Book = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp
)
val buttonText = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 13.sp,
    color = Color.White
)