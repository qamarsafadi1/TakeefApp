package com.selsela.takeefapp.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.selsela.takeefapp.data.config.model.AcType
import com.selsela.takeefapp.data.order.model.order.SelectedService
import com.selsela.takeefapp.data.order.model.order.SelectedServicesOrder
import com.selsela.takeefapp.utils.Constants.CLEANING
import com.selsela.takeefapp.utils.Constants.INSTALLATION
import com.selsela.takeefapp.utils.Constants.MAINTENANCE
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val acTypes = LocalData.acTypes
    val services = LocalData.services
    var count = mutableStateOf(0)
    var selectedOrderService = mutableStateOf(SelectedServicesOrder())
    var acyTypes = mutableListOf<SelectedService>()
    var selectedAddress = mutableStateOf("")

    fun updateServiceToOrderItem() {
        val services = acyTypes
        selectedOrderService.value.services = services
        selectedOrderService.value.getTotalPrice()
        selectedOrderService.value.getMaintenanceCount()
        selectedOrderService.value.getCleaningCount()
        selectedOrderService.value.getInstallationCount()
    }

    fun addAcTypeCount(
        service: com.selsela.takeefapp.data.config.model.Service,
        count: Int,
        acyType: AcType
    ) {
        val newItem = SelectedService(
            service.id,
            service.price,
            count = count,
            acyTypeOd = acyType.id
        )
        if (count != 0) {
            val isFound =
                acyTypes.any { it.acyTypeOd == newItem.acyTypeOd && it.serviceId == newItem.serviceId }
            isFound.log("contains")
            val index = acyTypes.indexOfFirst {
                it.acyTypeOd == newItem.acyTypeOd
            }
            if (!isFound) {
                acyTypes.add(
                    SelectedService(
                        service.id,
                        service.price,
                        count = count,
                        acyTypeOd = acyType.id
                    )
                )
            } else {
                if (index != -1) {
                    acyTypes[index] = SelectedService(
                        service.id,
                        service.price,
                        count = count,
                        acyTypeOd = acyType.id
                    )
                }
            }
            acyTypes.distinctBy { Pair(it.acyTypeOd, it.serviceId) }
                .log("   viewModel.acyTypes")
        } else {
            val newItem = SelectedService(
                service.id,
                service.price,
                count = count,
                acyTypeOd = acyType.id
            )
            acyTypes.removeIf {
                it.acyTypeOd == newItem.acyTypeOd && it.serviceId == newItem.serviceId
            }
            // updateServiceToOrderItem()
            //  selectedOrderService.value.services = acyTypes
        }

    }

    fun updateSelectedAddress(newAddress: String){
        selectedAddress.value = newAddress
    }
}
