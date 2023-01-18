package com.selsela.takeefapp.utils.retrofit.model

import com.google.gson.annotations.SerializedName

data class ErrorBase(
        val `data`: Any? = Any(),
        val errors: List<Errors>?= listOf(),
        @SerializedName("response_message")
        val responseMessage: String? =null,
        val count_error: String?=null,
        val error_type: String?=null,
        val status: Boolean
)