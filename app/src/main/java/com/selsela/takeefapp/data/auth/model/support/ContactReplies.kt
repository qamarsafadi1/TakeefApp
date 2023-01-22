package com.selsela.takeefapp.data.auth.model.support


import com.google.gson.annotations.SerializedName

data class ContactReplies(
    @SerializedName("actor_id")
    val actorId: Int? = 0,
    @SerializedName("actor_type")
    val actorType: String? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("is_seen")
    val isSeen: Int? = 0,
    @SerializedName("mobile")
    val mobile: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("replies")
    val replies: List<Reply>? = listOf(),
    @SerializedName("text")
    val text: String? = "",
    @SerializedName("updated_at")
    val updatedAt: String? = ""
)