package com.selsela.takeefapp.data.config.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class AcType(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    var count:Int =0,
)