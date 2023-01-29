package com.selsela.takeefapp.data.auth.model.auth


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("accessToken")
    val accessToken: String = "",
    @SerializedName("activation_code")
    val activationCode: String = "",
    @SerializedName("avatar")
    val avatar: String = "http://airconditionars.selselatech.com/uploads/blank.png",
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
    val name: String = "عميل",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("balance")
    val balance: Double = 0.0,
    @SerializedName("specific_orders")
    var specificOrders: Int = 0,
    @SerializedName("new_orders")
    val newOrders: Int = 0,
    @SerializedName("processing_orders")
    val processingOrders: Int = 0,
    @SerializedName("archive_orders")
    val archiveOrders: Int = 0,
    @SerializedName("new_notifications")
    val newNotifications: Int = 0,
)