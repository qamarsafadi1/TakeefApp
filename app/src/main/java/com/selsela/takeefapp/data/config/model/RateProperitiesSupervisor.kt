package com.selsela.takeefapp.data.config.model

import androidx.annotation.Keep

@Keep
data class RateProperitiesSupervisor(
    val id: Int = 0,
    val name: String = "",
    var rate: Float = 0f,
)