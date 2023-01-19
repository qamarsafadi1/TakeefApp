package com.selsela.takeefapp.data.auth.model.auth


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("accessToken")
    val accessToken: String = "",
    @SerializedName("activation_code")
    val activationCode: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("country")
    val country: Country = Country(),
    @SerializedName("country_id")
    val countryId: Int = 0,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("language")
    val language: String = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("status")
    val status: String = ""
)