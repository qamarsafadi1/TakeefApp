package com.selsela.takeefapp.data.config.model.page


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PageResponse(
    @SerializedName("page")
    val page: Page = Page(),
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false
)