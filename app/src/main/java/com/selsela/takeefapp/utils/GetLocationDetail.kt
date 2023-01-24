package com.selsela.takeefapp.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.util.Locale

class GetLocationDetail(val context: Context) {

    fun getCurrentAddress(lat: Double, lang: Double): String? {
        var addresses: List<Address>? = null
        val locale = Locale(LocalData.appLocal ?: "en")
        val geocode = Geocoder(context, locale)
        if (Geocoder.isPresent()) {
            try {
                addresses = geocode.getFromLocation(lat, lang, 1)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            if (addresses != null && addresses.isNotEmpty()) {
                val myAddress: String? = addresses[0].getAddressLine(0)
                val admin: String? = addresses[0].adminArea
                val subAdmin: String? = addresses[0].subAdminArea
                val subLocality: String? = addresses[0].subLocality
                val featureName: String? = addresses[0].featureName
                val locality: String? = addresses[0].locality
                val premises: String? = addresses[0].premises
                val thoroughfare: String? = addresses[0].thoroughfare
                val subThoroughfare: String? = addresses[0].subThoroughfare
                if (addresses.isNotEmpty()) {
                    val line = addresses[0].getAddressLine(0).split(",")
                    if (line.isNotEmpty()) {
                        Log.d("line", addresses[0].getAddressLine(0) + "::")
                        Log.d(
                            "line0",
                            addresses[0].getAddressLine(0).substringAfter(",") + "::"
                        )
                    }

                }
                Log.d("myAddress", "$myAddress::")
                Log.d("admin", "$admin::")
                Log.d("subAdmin", "$subAdmin::")
                Log.d("subLocality", "$subLocality::")
                Log.d("featureName", "$featureName::")
                Log.d("locality", "$locality::")
                Log.d("premises", "$premises::")
                Log.d("thoroughfare", "$thoroughfare::")
                Log.d("subThoroughfare", "$subThoroughfare::")
                return myAddress
            }
        }
        return ""
        //  { viewModelDistance.getGeoAddress("$lat,$lang") }.withDelay(1000)
    }

}