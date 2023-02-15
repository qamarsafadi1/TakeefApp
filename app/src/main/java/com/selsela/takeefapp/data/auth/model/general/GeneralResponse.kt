package com.selsela.takeefapp.data.auth.model.general


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GeneralResponse(
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("returned")
    val returned: Any? = Any(),
    @SerializedName("status")
    val status: Boolean = false
)