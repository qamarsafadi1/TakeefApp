package com.selsela.takeefapp.data.order.model.order


import com.google.gson.annotations.SerializedName

data class OrderService(
    @SerializedName("ac_type")
    val acType: AcType = AcType(),
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("is_calculated_in_total")
    val isCalculatedInTotal: Int = 0,
    @SerializedName("service")
    val service: Service = Service(),
    @SerializedName("service_price")
    val servicePrice: Int = 0,
    @SerializedName("total_service_price")
    val totalServicePrice: Int = 0
)