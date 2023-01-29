package com.selsela.takeefapp.data.order.model.order


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("additional_cost")
    var additionalCost: Double = 0.0,
    @SerializedName("app_percent")
    val appPercent: Int = 0,
    @SerializedName("app_value")
    val appValue: Int = 0,
    @SerializedName("grand_total")
    val grandTotal: Double = 0.0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("order_id")
    val orderId: Int = 0,
    @SerializedName("paid_additional")
    val paidAdditional: Int = 0,
    @SerializedName("paid_additional_cash")
    val paidAdditionalCash: Int = 0,
    @SerializedName("paid_additional_online")
    val paidAdditionalOnline: Int = 0,
    @SerializedName("paid_additional_wallet")
    val paidAdditionalWallet: Int = 0,
    @SerializedName("paid_cash")
    val paidCash: Int = 0,
    @SerializedName("paid_minus")
    val paidMinus: Int = 0,
    @SerializedName("paid_online")
    val paidOnline: Int = 0,
    @SerializedName("paid_wallet")
    val paidWallet: Double = 0.0,
    @SerializedName("service_total")
    val serviceTotal: Int = 0,
    @SerializedName("tax_percent")
    val taxPercent: Int = 0,
    @SerializedName("tax_value")
    val taxValue: Int = 0,
    @SerializedName("transaction")
    val transaction: Any? = Any()
)