package com.selsela.takeefapp.utils.retrofit.model

import androidx.annotation.Keep
import com.selsela.takeefapp.utils.retrofit.model.Errors

@Keep
data class ErrorsData(
    val errors: List<Errors>?= listOf(),
    val responseMessage: String? =null,
    val status: Int? = 0
)