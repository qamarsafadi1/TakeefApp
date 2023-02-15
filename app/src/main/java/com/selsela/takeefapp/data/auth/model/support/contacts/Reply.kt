package com.selsela.takeefapp.data.auth.model.support.contacts


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Reply(
    @SerializedName("actor_id")
    val actorId: Any? = Any(),
    @SerializedName("actor_type")
    val actorType: Any? = Any(),
    @SerializedName("admin_id")
    val adminId: Int? = 0,
    @SerializedName("contact_id")
    val contactId: Int? = 0,
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("updated_at")
    val updatedAt: String? = ""
)