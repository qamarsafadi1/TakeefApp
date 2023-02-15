package com.selsela.takeefapp.data.order.model.order


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Supervisor(
    @SerializedName("area")
    val area: Area = Area(),
    @SerializedName("available_status")
    val availableStatus: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("city")
    val city: City = City(),
    @SerializedName("country")
    val country: Country = Country(),
    @SerializedName("country_id")
    val countryId: Int = 0,
    @SerializedName("district")
    val district: District = District(),
    @SerializedName("email")
    val email: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("latitude")
    val latitude: Double? = 31.509229,
    @SerializedName("longitude")
    val longitude: Double? =  34.456082,
)