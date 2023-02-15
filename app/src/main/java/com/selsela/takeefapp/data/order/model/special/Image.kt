package com.selsela.takeefapp.data.order.model.special


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Image(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("specific_order_id")
    val specificOrderId: Int = 0
)