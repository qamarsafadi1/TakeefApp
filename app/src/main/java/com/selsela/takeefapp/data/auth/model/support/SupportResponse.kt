package com.selsela.takeefapp.data.auth.model.support


import com.google.gson.annotations.SerializedName
import com.selsela.takeefapp.data.auth.model.support.ContactReplies

data class SupportResponse(
    @SerializedName("contact")
    val contactReplies: ContactReplies? = ContactReplies(),
    @SerializedName("response_message")
    val responseMessage: String? = "",
    @SerializedName("status")
    val status: Boolean? = false
)