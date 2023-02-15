package com.selsela.takeefapp.utils.retrofit.model

import androidx.annotation.Keep

@Keep
data class Errors(
    val error: String,
    val `field`: String
)