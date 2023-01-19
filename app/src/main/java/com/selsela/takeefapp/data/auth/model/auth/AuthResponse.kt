package com.selsela.takeefapp.data.auth.model.auth


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false,
    @SerializedName("user")
    val user: User = User()
)