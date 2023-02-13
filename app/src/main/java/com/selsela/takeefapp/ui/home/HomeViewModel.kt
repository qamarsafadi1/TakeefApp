package com.selsela.takeefapp.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.selsela.takeefapp.data.auth.model.address.FavouriteAddresse
import com.selsela.takeefapp.data.auth.repository.AuthRepository
import com.selsela.takeefapp.data.config.model.AcType
import com.selsela.takeefapp.data.config.model.Service
import com.selsela.takeefapp.data.config.model.WorkPeriod
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.data.order.model.order.SelectedService
import com.selsela.takeefapp.data.order.model.order.SelectedServicesOrder
import com.selsela.takeefapp.data.order.repository.OrderRepository
import com.selsela.takeefapp.ui.order.OrderState
import com.selsela.takeefapp.utils.Constants.CLEANING
import com.selsela.takeefapp.utils.Constants.INSTALLATION
import com.selsela.takeefapp.utils.Constants.MAINTENANCE
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.Extensions.Companion.calculateDiscount
import com.selsela.takeefapp.utils.Extensions.Companion.getMyLocation
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.fromJson
import com.selsela.takeefapp.utils.retrofit.model.ErrorsData
import com.selsela.takeefapp.utils.retrofit.model.Status
import com.selsela.takeefapp.utils.toJson
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * UiState for the Special Order
 */
data class OrderUiState(
    val onSuccess: StateEvent = consumed,
    val order: Order? = null,
    val isLoading: Boolean = false,
    val onFailure: StateEventWithContent<ErrorsData> = consumed(),
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    var count = mutableStateOf(0)
    var address: Address? = null
    var selectedOrderService = mutableStateOf(SelectedServicesOrder())
    var acyTypes = mutableListOf<SelectedService>()
    var selectedAddress = mutableStateOf("")
    var selectedPeriodId = mutableStateOf(WorkPeriod())
    var selectedPaymentId = mutableStateOf(-1)
    var currentLocation = mutableStateOf(LatLng(0.0, 0.0))
    var tax = ""
    var useWallet = 0


    /**
     * State Subscribers
     */
    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()
    private var state: OrderUiState
        get() = _uiState.value
        set(newState) {
            _uiState.update { newState }
        }


    fun updateServiceToOrderItem() {
        val services = acyTypes
        selectedOrderService.value.services = services
        selectedOrderService.value.getTotalPrice()
        services.log("services")
        selectedOrderService.value.getMaintenanceCount()
        selectedOrderService.value.getCleaningCount()
        selectedOrderService.value.getInstallationCount()
    }

    fun getServicesList(): MutableState<List<Service>?> {
        "heyList".log()
        LocalData.services?.log("heyList")
        return mutableStateOf(LocalData.services)
    }

    fun getAcTypesList(): MutableState<List<AcType>?> {
        "heyList".log()
        LocalData.acTypes?.log("heyList")
        return mutableStateOf(LocalData.acTypes)
    }

    fun addAcTypeCount(
        service: Service,
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
            val index = acyTypes.indexOfFirst {
                it.acyTypeOd == newItem.acyTypeOd && it.serviceId == newItem.serviceId
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
            removeItem(service, count, acyType)
        }

    }

    private fun removeItem(
        service: Service,
        count: Int,
        acyType: AcType
    ) {
        val newItem = SelectedService(
            service.id,
            service.price,
            count = count,
            acyTypeOd = acyType.id
        )
        acyTypes.removeIf {
            it.acyTypeOd == newItem.acyTypeOd && it.serviceId == newItem.serviceId
        }
    }

    fun updateSelectedAddress(newAddress: String, latLng: LatLng) {
        selectedAddress.value = newAddress
        address?.lat = latLng.latitude
        address?.lng = latLng.longitude
        currentLocation.value = latLng

    }

    fun selectDate(date: String) {
        selectedOrderService.value.orderDate = date
    }

    fun selectPayment(paymentId: Int) {
        selectedPaymentId.value = paymentId
    }

    fun selectWorkPeriod(workPeriodId: WorkPeriod) {
        selectedOrderService.value.workPeriodId = workPeriodId.id
        selectedPeriodId.value = workPeriodId
    }

    fun getTotalPrice(): String {
        return selectedOrderService.value.totalServicesPrice?.value?.plus(tax.toFloat()).toString()
    }

    fun calculateTax(): String {
        tax = "".calculateDiscount(
            selectedOrderService.value.totalServicesPrice?.value ?: 0.0,
            LocalData.configurations?.taxPercent?.toFloat() ?: 0f
        )
        return tax
    }

    fun isWalletEnough(): Boolean {
        val isEnough = (LocalData.userWallet?.balance ?: 0.0) > getTotalPrice().toDouble()
        useWallet = if (isEnough) 1
        else if (LocalData.userWallet?.balance != 0.0) {
            1
        } else 0
        return isEnough
    }

    fun isWalletEnough(total: Double): Boolean {
        val isEnough = (LocalData.userWallet?.balance ?: 0.0) > total
        useWallet = if (isEnough) 1
        else if (LocalData.userWallet?.balance != 0.0) {
            1
        } else 0
        return isEnough
    }

    fun getCount(serviceId: Int): Int {
        return when (serviceId) {
            MAINTENANCE -> selectedOrderService.value.maintenanceCount?.value ?: 0
            CLEANING -> selectedOrderService.value.cleanCount?.value ?: 0
            else -> selectedOrderService.value.installCount?.value ?: 0
        }
    }

    private fun getServices(): String {
        return selectedOrderService.value.services
            .map {
                listOf(it.serviceId, it.acyTypeOd, it.count)
            }.toString()
    }

    fun placeOrder() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.placeOrder(
                services = getServices(),
                orderDate = selectedOrderService.value.orderDate,
                workPeriodId = selectedOrderService.value.workPeriodId,
                useWallet = useWallet,
                paymentTypeId = selectedPaymentId.value,
                areaId = address?.areaId ?: -1,
                cityId = address?.cityId ?: -1,
                districtId = address?.districtId ?: -1,
                lat = address?.lat ?: 0.0,
                lng = address?.lng ?: 0.0,
                isFav = address?.isFav ?: 0,
                note = address?.note

            ).collect { result ->
                val orderUiState = when (result.status) {
                    Status.SUCCESS -> {
                        OrderUiState(
                            onSuccess = triggered
                        )
                    }

                    Status.LOADING ->
                        OrderUiState(
                            isLoading = true
                        )

                    Status.ERROR -> OrderUiState(
                        onFailure = triggered(
                            ErrorsData(
                                result.errors,
                                result.message,
                            )
                        ),
                    )
                }
                state = orderUiState
            }
        }

    }

    /**
     * reset handlers
     */
    fun onSuccess() {
        viewModelScope.launch {
            authRepository.getWallet()

        }
        state = state.copy(onSuccess = consumed)
        selectedOrderService.value = SelectedServicesOrder()
        selectedAddress.value = ""
        selectedPeriodId.value = WorkPeriod()
        selectedPaymentId.value = -1
        acyTypes.clear()

    }

    fun onFailure() {
        state = state.copy(onFailure = consumed())
    }

    fun selectAddressFromFav(favAddress: FavouriteAddresse) {
        val selectAddress = Address(
            areaId = favAddress.area.id,
            cityId = favAddress.city.id,
            districtId = favAddress.district.id,
            lat = favAddress.latitude,
            lng = favAddress.longitude,
            note = favAddress.note,
            isFav = 1,
        )
        address = selectAddress
        updateSelectedAddress(
            favAddress.getFullAddress(),
            LatLng(favAddress.latitude, favAddress.longitude)
        )
        currentLocation.value = LatLng(favAddress.latitude, favAddress.longitude)


    }

    fun selectAddressFromFav(favAddress: Place, isFav: Int) {

        var areaId = -1
        var cityId = -1
        var districtId = -1

        val area = LocalData.ciites?.find {
            favAddress.address.contains(it.name)
        }
        val city = area?.cities?.findLast {
            favAddress.address.contains(it.name)
        }
        val district = city?.children?.findLast {
            favAddress.address.contains(it.name)
        }

        cityId = city?.id ?: -1
        areaId = area?.id ?: -1
        districtId = district?.id ?: -1

        val selectAddress = Address(
            areaId = areaId,
            cityId = cityId,
            districtId = districtId,
            lat = favAddress.latLng.latitude,
            lng = favAddress.latLng.longitude,
            note = "",
            isFav = isFav,
        )
        address = selectAddress
        updateSelectedAddress(
            favAddress.address,
            LatLng(favAddress.latLng.latitude, favAddress.latLng.longitude)
        )
        currentLocation.value = LatLng(favAddress.latLng.latitude, favAddress.latLng.longitude)

    }

    fun getCost(service: Service): String {
        val cost = if (service.multipleCount == 0) service.price
        else {
            when (service.id) {
                CLEANING -> service.price.times(selectedOrderService.value.cleanCount?.value ?: 1)
                INSTALLATION -> service.price.times(
                    selectedOrderService.value.installCount?.value ?: 1
                )

                else -> service.price
            }

        }
        cost.log("cost")
        return cost.toString()
    }
}


data class Address(
    val areaId: Int,
    val cityId: Int,
    val districtId: Int,
    var lat: Double? = 0.0,
    var lng: Double? = 0.0,
    val note: String? = "",
    var isFav: Int = 0
)
