package com.selsela.takeefapp.data.order.model.order


import com.google.gson.annotations.SerializedName
import com.selsela.takeefapp.utils.Extensions.Companion.log

data class Order(
    @SerializedName("case")
    val case: Case = Case(),
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("grand_total")
    val grandTotal: Double = 0.0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("address")
    val address: Address = Address(),
    @SerializedName("logs")
    val logs: List<Log> = listOf(),
    @SerializedName("number")
    val number: String = "",
    @SerializedName("order_date")
    val orderDate: String = "",
    @SerializedName("order_service")
    val orderService: List<OrderService> = listOf(),
    @SerializedName("supervisor")
    val supervisor: Supervisor? = Supervisor(),
    @SerializedName("transaction")
    val transaction: Transaction = Transaction(),
    @SerializedName("work_period")
    val workPeriod: WorkPeriod = WorkPeriod(),
    @SerializedName("use_wallet")
    val useWallet: Int = 0,
    @SerializedName("is_paid")
    val isPaid: Int = 0,
    @SerializedName("is_rated")
    val isRated: Int = 0,
    @SerializedName("need_additional_cost")
    val needAdditionalCost: Int = 0,
    @SerializedName("price")
    val price: Price = Price(),
    @SerializedName("payment")
    val payment: Payment = Payment(),
) {
    fun getServices(): List<OrderServiceItem> {
        val orderServicesList = mutableListOf<OrderServiceItem>()
        val acTypes = mutableListOf<AcTypes>()
        var service = OrderService()
        orderService.forEachIndexed { index, item ->
            if (orderService.find { it.service.id == item.service.id } != null) {
                service = item
                acTypes.add(
                    AcTypes(
                        item.acType,
                        item.count,
                        item.isCalculatedInTotal,
                        item.servicePrice,
                        item.totalServicePrice
                    )
                )
            }
        }
        orderServicesList.add(OrderServiceItem(service,acTypes))
        orderServicesList.distinctBy { it.service.service.id }
        orderServicesList.log("orderServicesList")
        return orderServicesList
    }
}