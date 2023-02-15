package com.selsela.takeefapp.data.config.model

import androidx.annotation.Keep
import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.utils.Constants.CLEANING
import com.selsela.takeefapp.utils.Constants.MAINTENANCE

@Keep
data class Service(
    val description: String = "",
    val id: Int = 0,
    @SerializedName("image_utl")
    val imageUtl: String = "",
    @SerializedName("multiple_count")
    val multipleCount: Int = 0,
    val name: String = "",
    val price: Double = 0.0
) {

    fun imageBackgroundColor(): Color {
        return when (id) {
            MAINTENANCE -> Purple40
            CLEANING -> LightBlue
            else -> SecondaryColor

        }
    }

    fun cellBg(): Color {
        return when (id) {
            MAINTENANCE -> Purple40.copy(0.10f)
            CLEANING -> LightBlue.copy(0.10f)
            else -> SecondaryColor.copy(0.10f)

        }
    }
}
