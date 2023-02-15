package com.selsela.takeefapp.data.order.model.order


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Service(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image_utl")
    val imageUtl: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("price")
    val price: Double = 0.0
)