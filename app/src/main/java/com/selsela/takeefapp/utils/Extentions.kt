package com.selsela.takeefapp.utils

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.selsela.takeefapp.ui.theme.TextColor

class Extentions {
    companion object{
        fun <T> (() -> T).withDelay(delay: Long = 250L) {
            Handler(Looper.getMainLooper()).postDelayed({ this.invoke() }, delay)
        }
    }
}