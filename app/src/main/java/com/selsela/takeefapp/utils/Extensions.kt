package com.selsela.takeefapp.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

class Extensions {
    companion object{
        fun <T> (() -> T).withDelay(delay: Long = 250L) {
            Handler(Looper.getMainLooper()).postDelayed({ this.invoke() }, delay)
        }
        fun Context.findDrawable(color: Int): Int {
            return ContextCompat.getColor(this, color)
        }
        fun Any.log(tag: String = "") {
            if (tag.equals("")) {
                Log.d("QMR : ", this.toString())
            } else {
                Log.d("QMR$tag", this.toString())

            }
        }
        fun NavOptionsBuilder.navigateWithClearBackStack(navController: NavController) {
            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
                inclusive =  true
            }
        }
    }
}