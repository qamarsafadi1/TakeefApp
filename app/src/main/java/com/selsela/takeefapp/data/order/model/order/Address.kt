package com.selsela.takeefapp.data.order.model.order


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("area")
    val area: Area = Area(),
    @SerializedName("city")
    val city: City = City(),
    @SerializedName("district")
    val district: District = District(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("is_fav")
    val isFav: Int = 0,
    @SerializedName("latitude")
    val latitude: Int = 0,
    @SerializedName("longitude")
    val longitude: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("note")
    val note: String = ""
)