package com.selsela.takeefapp.data.config.model.payments


import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("icon_url")
    val iconUrl: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: String = ""
)