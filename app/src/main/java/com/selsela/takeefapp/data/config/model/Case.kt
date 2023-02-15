package com.selsela.takeefapp.data.config.model

import androidx.annotation.Keep

@Keep
data class Case(
    val canCancel: Int = 0,
    val canRate: Int = 0,
    val id: Int = 0,
    val name: String = ""
)