package com.selsela.takeefapp.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@SuppressLint("ModifierFactoryUnreferencedReceiver")
object ModifiersExtension {

    fun Modifier.paddingTop(num: Double) = Modifier.padding(top = num.dp)

    fun Modifier.paddingTop(num: Int) = Modifier.padding(top = num.dp)

    fun Modifier.widthMatchParent(height: Int) = Modifier.fillMaxWidth(1f).requiredHeight(height = height.dp)

    fun Modifier.cornerRadius(
         color: Color,
         radius: Int) = Modifier.background(
         color = color,
         shape = RoundedCornerShape(radius)
         )
    fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }
}