package com.selsela.takeefapp.data.config.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ConfigResponse(
    @SerializedName("ac_types")
    val acTypes: List<AcType> = listOf(),
    val cases: List<Case> = listOf(),
    val configurations: Configurations = Configurations(),
    @SerializedName("rate_properities_supervisor")
    val rateProperitiesSupervisor: List<RateProperitiesSupervisor> = listOf(),
    @SerializedName("rate_properities_user")
    val rateProperitiesUser: List<RateProperitiesUser> = listOf(),
    val responseMessage: String = "",
    val services: List<Service> = listOf(),
    val status: Boolean = false,
    @SerializedName("work_period")
    val workPeriod: List<WorkPeriod> = listOf()
)