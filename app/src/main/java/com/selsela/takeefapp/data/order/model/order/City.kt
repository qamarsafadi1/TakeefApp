package com.selsela.takeefapp.data.order.model.order


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class City(
    @SerializedName("country_id")
    val countryId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("orders_count")
    val ordersCount: Int = 0,
    @SerializedName("parent_id")
    val parentId: Int = 0
)