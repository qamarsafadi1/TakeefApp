package com.selsela.takeefapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.core.content.ContextCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.selsela.takeefapp.R
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
            val title =  when(currentRoute.destination.route){
                Destinations.HOME_SCREEN,Destinations.LOGIN_SCREEN,
                Destinations.VERIFY_SCREEN,Destinations.SUCCESS,Destinations.SPECIAL_ORDER -> ""
                Destinations.SEARCH_ADDRESS_SCREEN_WITH_ARGUMENT -> this.context.getString(R.string.chosse_address)
                Destinations.REVIEW_ORDER -> this.context.getString(R.string.review_order)
                else -> "Selsela"
            }
            return title
        }
        fun NavController.showingBackButton(currentRoute: NavBackStackEntry): Boolean{
            var showBackButton =  when(currentRoute.destination.route){
                Destinations.HOME_SCREEN,Destinations.SPLASH_SCREEN,Destinations.SUCCESS -> false
                else -> true
            }
            return showBackButton
        }
        fun NavOptionsBuilder.navigateWithClearBackStack(navController: NavController) {
            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
                inclusive =  true
            }
        }

        @Composable
        fun Context.RequestPermission(
            permission: String,
            onGranted: (Boolean) -> Unit
        ){
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                onGranted(isGranted)
            }
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) -> {
                    onGranted(true)
                }
                else -> {
                    SideEffect { // SideEffect just when you need to request your permission
                        // first time before composition
                        launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                }
            }


        }
        fun getMyLocation(
            context: Context,
            onMyLocation: (LatLng) -> Unit
        ) {
            try {
                val fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(context)
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        val currentLocation = LatLng(location.latitude, location.longitude)
                        onMyLocation(currentLocation)
                    } ?: run {}
                }

            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        fun bitmapDescriptor(
            context: Context,
            vectorResId: Int
        ): BitmapDescriptor? {

            // retrieve the actual drawable
            val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val bm = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            // draw it onto the bitmap
            val canvas = android.graphics.Canvas(bm)
            drawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bm)
        }
    }
}