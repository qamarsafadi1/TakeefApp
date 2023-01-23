package com.selsela.takeefapp.data.order.model.order


import com.google.gson.annotations.SerializedName

data class Log(
    @SerializedName("case")
    val case: com.selsela.takeefapp.data.config.model.Case = com.selsela.takeefapp.data.config.model.Case(),
    @SerializedName("case_id")
    val caseId: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("order_id")
    val orderId: Int = 0
)