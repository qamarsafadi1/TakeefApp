package com.selsela.takeefapp.data.config.model.city


import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("areas")
    val areas: List<Area> = listOf(),
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false
)