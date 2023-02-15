package com.selsela.takeefapp.data.auth.model.address


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AddressResponse(
    @SerializedName("addresses")
    val addresses: List<Addresse> = listOf(),
    @SerializedName("favourite_addresses")
    val favouriteAddresses: List<FavouriteAddresse> = listOf(),
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false
)