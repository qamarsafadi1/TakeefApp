package com.selsela.takeefapp.data.config.model.page


import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("text")
    val text: String = "",
    @SerializedName("title")
    val title: String = ""
)