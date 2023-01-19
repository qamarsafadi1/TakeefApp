package com.selsela.takeefapp.data.auth.model.general


import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("returned")
    val returned: Any? = Any(),
    @SerializedName("status")
    val status: Boolean = false
)