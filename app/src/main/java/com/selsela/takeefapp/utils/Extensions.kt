package com.selsela.takeefapp.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.selsela.takeefapp.navigation.Destinations
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class Extensions {
    companion object{
        fun <T> (() -> T).withDelay(delay: Long = 250L) {
            Handler(Looper.getMainLooper()).postDelayed({ this.invoke() }, delay)
        }
        fun Context.findDrawable(color: Int): Int {
            return ContextCompat.getColor(this, color)
        }

        fun Int.convertToDecimalPatter(): String{
           return DecimalFormat(
                "00",
                DecimalFormatSymbols(Locale.US)
            ).format(this)
        }
        fun Any.log(tag: String = "") {
            if (tag.equals("")) {
                Log.d("QMR : ", this.toString())
            } else {
                Log.d("QMR$tag", this.toString())

            }
        }
        fun NavController.bindToolbarTitle(currentRoute: NavBackStackEntry): String{
            var title =  when(currentRoute.destination.route){
                Destinations.HOME_SCREEN,Destinations.LOGIN_SCREEN -> ""
                else -> "Selsela"
            }
            return title
        }
        fun NavController.showingBackButton(currentRoute: NavBackStackEntry): Boolean{
            var showBackButton =  when(currentRoute.destination.route){
                Destinations.HOME_SCREEN,Destinations.SPLASH_SCREEN -> false
                else -> true
            }
            return showBackButton
        }
        fun NavOptionsBuilder.navigateWithClearBackStack(navController: NavController) {
            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
                inclusive =  true
            }
        }
    }
}