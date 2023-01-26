package com.selsela.takeefapp.data.auth.model.address


import com.google.gson.annotations.SerializedName

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