package com.selsela.takeefapp.data.auth.model.notifications


import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("notifications")
    val notifications: List<Notification> = listOf(),
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false
)