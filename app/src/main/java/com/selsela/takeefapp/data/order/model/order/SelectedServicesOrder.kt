package com.selsela.takeefapp.data.order.model.order

import androidx.annotation.Keep
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.selsela.takeefapp.utils.Constants.CLEANING
import com.selsela.takeefapp.utils.Constants.INSTALLATION
import com.selsela.takeefapp.utils.Constants.MAINTENANCE
import com.selsela.takeefapp.utils.Extensions.Companion.log

@Keep
data class SelectedServicesOrder(
    var services: List<SelectedService> = listOf(),
    val paymentId: Int = -1,
    var orderDate: String = "",
    var workPeriodId: Int = -1,
    var totalServicesPrice: MutableState<Double>? = mutableStateOf(0.0),
    var maintenanceCount: MutableState<Int>? = mutableStateOf(0),
    var cleanCount: MutableState<Int>? = mutableStateOf(0),
    var installCount: MutableState<Int>? = mutableStateOf(0),
) {
    fun getTotalPrice() {
        val total =
            if (services.any { it.serviceId == CLEANING || it.serviceId == INSTALLATION }) {
                services.sumOf {
                    if (it.serviceId != MAINTENANCE) {
                        it.servicePrice.times(it.count)
                    } else {
                        0.0
                    }
                }
            } else {
                services.find {it.serviceId == MAINTENANCE}?.servicePrice ?: 0.0
            }
        totalServicesPrice?.value = total
    }

    fun getMaintenanceCount() {
        val count = services.filter {
            it.serviceId == MAINTENANCE
        }.sumOf {
            it.count
        }
        maintenanceCount?.value = count
    }

    fun getCleaningCount() {
        val count = services.filter {
            it.serviceId == CLEANING

        }.sumOf {
            it.count
        }
        cleanCount?.value = count
    }

    fun getInstallationCount() {
        val count = services.filter {
            it.serviceId == INSTALLATION
        }.sumOf {
            it.count
        }
        installCount?.value = count
    }

}

@Keep
data class SelectedService(
    val serviceId: Int = -1,
    val servicePrice: Double = 0.0,
    val acyTypeOd: Int = -1,
    val count: Int = 0,
) {

}
