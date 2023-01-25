package com.selsela.takeefapp.data.config.model.city


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("children")
    val children: List<Children> = listOf(),
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