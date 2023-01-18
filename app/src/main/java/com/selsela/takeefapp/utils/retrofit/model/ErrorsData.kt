package com.selsela.takeefapp.utils.retrofit.model

import com.selsela.takeefapp.utils.retrofit.model.Errors

data class ErrorsData(
    val errors: List<Errors>?= listOf(),
    val responseMessage: String? =null,
)