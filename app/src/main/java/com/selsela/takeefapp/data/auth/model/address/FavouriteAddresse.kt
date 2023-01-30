package com.selsela.takeefapp.data.auth.model.address


import com.google.gson.annotations.SerializedName

data class FavouriteAddresse(
    @SerializedName("area")
    val area: Area = Area(),
    @SerializedName("city")
    val city: City = City(),
    @SerializedName("district")
    val district: District = District(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("is_fav")
    var isFav: Int = 0,
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("longitude")
    val longitude: Double = 0.0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("note")
    val note: String = ""
) {
    fun getFullAddress(): String {

        return "${area.name},${city.name},${district.name}"
    }
}