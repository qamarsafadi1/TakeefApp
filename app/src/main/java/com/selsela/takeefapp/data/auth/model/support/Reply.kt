package com.selsela.takeefapp.data.auth.model.support


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.selsela.takeefapp.utils.DateHelper

@Keep
data class Reply(
    @SerializedName("actor_id")
    val actorId: Any? = Any(),
    @SerializedName("actor_type")
    val actorType: Any? = Any(),
    @SerializedName("owner_id")
    val adminId: Int? = 1,
    @SerializedName("contact_id")
    val contactId: Int? = 0,
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("updated_at")
    val updatedAt: String? = "",
    var type: String? = "admin"
) {
    fun showTime(): String {
        return if (createdAt.isNullOrEmpty().not())
            DateHelper.formatTime(DateHelper.getTimeStamp(createdAt ?: ""))
        else ""
    }
}