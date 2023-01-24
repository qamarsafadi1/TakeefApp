package com.selsela.takeefapp.data.order.model.order


import com.google.gson.annotations.SerializedName

data class OrderService(
    @SerializedName("ac_types")
    val acType: List<AcTypes> = listOf(),
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("is_calculated_in_total")
    val isCalculatedInTotal: Int = 0,
    @SerializedName("service")
    val service: Service = Service(),
    @SerializedName("service_price")
    val servicePrice: Double = 0.0,
    @SerializedName("price")
    val totalServicePrice: Double = 0.0,
    val acTypes: MutableList<AcTypes> = mutableListOf()
)

data class OrderServiceItem(
    @SerializedName("service")
    val service: OrderService = OrderService(),
    val acType: List<AcTypes> = listOf()
) {

}
data class AcTypes(
    @SerializedName("ac_type")
    val acType: AcType = AcType(),
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("is_calculated_in_total")
    val isCalculatedInTotal: Int = 0,
    @SerializedName("service_price")
    val servicePrice: Int = 0,
    @SerializedName("total_service_price")
    val totalServicePrice: Int = 0
) {

}