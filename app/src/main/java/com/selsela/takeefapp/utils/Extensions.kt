package com.selsela.takeefapp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
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
import com.selsela.takeefapp.utils.retrofit.model.ErrorBase
import com.selsela.takeefapp.utils.retrofit.model.Resource
import com.tapadoo.alerter.Alerter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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

        fun <T> Context.navigate(
            fromActivity: Context, toActivity: Class<T>, isLogin: Boolean? = true
        ) {
            val navIntent = Intent(fromActivity, toActivity)
            navIntent.putExtra("isLogin", isLogin)
            this.startActivity(navIntent)
            this.getActivity()?.finish()
        }

        fun Context.getActivity(): AppCompatActivity? = when (this) {
            is AppCompatActivity -> this
            is ContextWrapper -> baseContext.getActivity()
            else -> null
        }

        fun <E> handleSuccess(data: E?, message: String? = ""): MutableStateFlow<Resource<E>> {
            return MutableStateFlow(
                Resource.success(
                    data,
                    message = message
                )
            )
        }


        fun <E> handleExceptions(errorBase: Exception): MutableStateFlow<Resource<E>> {
            return MutableStateFlow(
                Resource.error(
                    null,
                    errorBase.message,
                    null
                )
            )
        }

        fun <E> handleExceptions(errorBase: ErrorBase): MutableStateFlow<Resource<E>> {
            return MutableStateFlow<Resource<E>>(
                Resource.error(
                    null,
                    errorBase.responseMessage,
                    errorBase.errors
                )
            )
        }


        fun Activity.showSuccessTop(message: String) {
            Alerter.create(this)
                .setTitle("")
                .setText(message)
                .setTitleAppearance(R.style.AlertTextAppearance_Title_1)
                .setTextAppearance(R.style.AlertTextAppearance_Text_1)
                .setBackgroundColorInt(Color.parseColor("#61BF61")) // or setBackgroundColorInt(Color.CYAN)
                .setIcon(R.drawable.component_6___2)
                .setIconColorFilter(0) //
                .show()
        }


        fun Activity.showErrorTop(message: String) {
            Alerter.create(this)
                .setTitle("")
                .setText(message)
                .setTitleAppearance(R.style.AlertTextAppearance_Title_1)
                .setTextAppearance(R.style.AlertTextAppearance_Text_1)
                .setBackgroundColorInt(Color.parseColor("#E54342")) // or setBackgroundColorInt(Color.CYAN)
                .setIcon(R.drawable.svgexport_10__10_)
                .setIconColorFilter(0) //
                .show()
        }

        @Composable
        fun <T> rememberFlow(
            flow: Flow<T>,
            lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
        ): Flow<T> {
            return remember(key1 = flow, key2 = lifecycleOwner) { flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED) }
        }
        @Composable
        fun <T : R, R> Flow<T>.collectAsStateLifecycleAware(
            initial: R,
            context: CoroutineContext = EmptyCoroutineContext
        ): State<R> {
            val lifecycleAwareFlow = rememberFlow(flow = this)
            return lifecycleAwareFlow.collectAsState(initial = initial, context = context)
        }
    }



}