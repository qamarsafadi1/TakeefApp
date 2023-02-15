package com.selsela.takeefapp.data.config.model

import androidx.annotation.Keep
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

@Keep
data class AcType(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    var count:Int =0,
)