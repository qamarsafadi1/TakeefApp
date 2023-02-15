package com.selsela.takeefapp.data.config.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.selsela.takeefapp.utils.DateHelper

@Keep
data class WorkPeriod(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    @SerializedName("time_from")
    val timeFrom: String = "",
    @SerializedName("time_to")
    val timeTo: String = ""
){
    fun getTimeFromFormatted(): String {
        return DateHelper.get24To12(timeFrom)
    }

    fun getTimeToFormatted(): String {
        return DateHelper.get24To12(timeTo)
    }
}