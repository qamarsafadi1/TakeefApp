package com.selsela.takeefapp.data.order.model.order


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.selsela.takeefapp.utils.DateHelper.Companion.get24To12

@Keep
data class WorkPeriod(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("time_from")
    val timeFrom: String = "",
    @SerializedName("time_to")
    val timeTo: String = ""
) {
    fun getTimeFromFormatted(): String {
        return get24To12(timeFrom)
    }

    fun getTimeToFormatted(): String {
        return get24To12(timeTo)
    }
}