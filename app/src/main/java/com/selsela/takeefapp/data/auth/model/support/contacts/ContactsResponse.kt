package com.selsela.takeefapp.data.auth.model.support.contacts


import com.google.gson.annotations.SerializedName
import com.selsela.takeefapp.data.auth.model.support.ContactReplies
import com.selsela.takeefapp.data.auth.model.support.contacts.Contact

data class ContactsResponse(
    @SerializedName("contacts")
    val contacts: List<ContactReplies>? = listOf(),
    @SerializedName("response_message")
    val responseMessage: String? = "",
    @SerializedName("status")
    val status: Boolean? = false
)