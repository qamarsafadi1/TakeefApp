package com.selsela.takeefapp.data.auth.model.address


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class District(
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