package com.selsela.takeefapp.data.order.model.order


import com.google.gson.annotations.SerializedName

data class AcType(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("status")
    val status: String = ""
)