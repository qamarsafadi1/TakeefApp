package com.selsela.takeefapp.data.config.model

import com.google.gson.annotations.SerializedName

data class ConfigResponse(
    @SerializedName("ac_types")
    val acTypes: List<AcType> = listOf(),
    val cases: List<Case> = listOf(),
    val configurations: Configurations = Configurations(),
    val rateProperitiesSupervisor: List<RateProperitiesSupervisor> = listOf(),
    val rateProperitiesUser: List<RateProperitiesUser> = listOf(),
    val responseMessage: String = "",
    val services: List<Service> = listOf(),
    val status: Boolean = false,
    val workPeriod: List<WorkPeriod> = listOf()
)