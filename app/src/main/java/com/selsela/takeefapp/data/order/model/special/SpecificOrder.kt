package com.selsela.takeefapp.data.order.model.special


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SpecificOrder(
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("images")
    val images: List<Image> = listOf(),
    @SerializedName("order_number")
    val orderNumber: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("user_name")
    val userName: String = ""
)