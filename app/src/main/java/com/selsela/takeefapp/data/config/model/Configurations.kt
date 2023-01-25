package com.selsela.takeefapp.data.config.model

import com.google.gson.annotations.SerializedName

data class Configurations(
    val address: String = "",
    val addressEn: String = "",
    val android: String = "",
    val androidVersion: String = "",
    val appPercent: String = "",
    val appStatusAndroid: String = "",
    val appStatusIos: String = "",
    val currencyAr: String = "",
    val currencyEn: String = "",
    val email: String = "",
    val facebook: String = "",
    val instagram: String = "",
    val ios: String = "",
    val iosVersion: String = "",
    val linkedIn: String = "",
    val mobile: String = "",
    val nameAr: String = "",
    val nameEn: String = "",
    @SerializedName("tax_percent")
    val taxPercent: String = "",
    val twitter: String = "",
    val updateAndroid: String = "",
    val updateIos: String = "",
    val whatsapp: String = ""
)