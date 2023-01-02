package com.selsela.takeefapp.utils

import android.os.Handler
import android.os.Looper

class Extentions {
    companion object{
        fun <T> (() -> T).withDelay(delay: Long = 250L) {
            Handler(Looper.getMainLooper()).postDelayed({ this.invoke() }, delay)
        }
    }
}