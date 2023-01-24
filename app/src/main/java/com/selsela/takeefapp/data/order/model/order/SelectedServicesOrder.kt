package com.selsela.takeefapp.data.order.model.order

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.selsela.takeefapp.utils.Constants.CLEANING
import com.selsela.takeefapp.utils.Constants.INSTALLATION
import com.selsela.takeefapp.utils.Constants.MAINTENANCE
import com.selsela.takeefapp.utils.Extensions.Companion.log

data class SelectedServicesOrder(
    var services: List<SelectedService> = listOf(),
    val paymentId: Int = -1,
    val orderDate: String = "",
    val workPeriodId: Int = -1,
    var totalServicesPrice: MutableState<Double> ? = mutableStateOf(0.0)
) {
    fun getTotalPrice() {
        val total =
            services.sumOf {
            if (it.serviceId != MAINTENANCE) {
                it.servicePrice.times(it.count)
            } else {
                if (services.any { it.serviceId != CLEANING || it.serviceId != INSTALLATION })
                    it.servicePrice
                else 0.0
            }
        }
        totalServicesPrice?.value = total
    }
}

data class SelectedService(
    val serviceId: Int = -1,
    val servicePrice: Double = 0.0,
    val acyTypeOd: Int = -1,
    val count: Int = 0,
) {

}
