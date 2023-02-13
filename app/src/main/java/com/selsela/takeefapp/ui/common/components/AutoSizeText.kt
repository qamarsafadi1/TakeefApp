package com.selsela.takeefapp.ui.common.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontLoader
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

@Composable
fun AutoSizeText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    minTextSize: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
) {
    BoxWithConstraints(modifier) {
        var combinedTextStyle = LocalTextStyle.current + style

        while (shouldShrink(text, combinedTextStyle, minTextSize, maxLines)) {
            combinedTextStyle = combinedTextStyle.copy(fontSize = combinedTextStyle.fontSize * .9f)
        }

        Text(
            text = text,
            style = style + TextStyle(fontSize = combinedTextStyle.fontSize),
            maxLines = maxLines,
            color = color
        )
    }
}


@Composable
private fun BoxWithConstraintsScope.shouldShrink(
    text: String,
    combinedTextStyle: TextStyle,
    minimumTextSize: TextUnit,
    maxLines: Int
): Boolean = if (minimumTextSize == TextUnit.Unspecified || combinedTextStyle.fontSize > minimumTextSize) {
    false
} else {
    val paragraph = Paragraph(
        text = text,
        style = combinedTextStyle,
        width = maxWidth.value,
        maxLines = maxLines,
        density = LocalDensity.current,
        resourceLoader = LocalFontLoader.current,
    )
    paragraph.height > maxHeight.value
}