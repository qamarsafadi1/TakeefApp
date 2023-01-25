package com.selsela.takeefapp.ui.address

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.selsela.takeefapp.data.config.model.AcType
import com.selsela.takeefapp.data.config.model.city.Children
import com.selsela.takeefapp.data.config.model.city.City
import com.selsela.takeefapp.data.order.model.order.SelectedService
import com.selsela.takeefapp.data.order.model.order.SelectedServicesOrder
import com.selsela.takeefapp.ui.home.Address
import com.selsela.takeefapp.utils.Constants.CLEANING
import com.selsela.takeefapp.utils.Constants.INSTALLATION
import com.selsela.takeefapp.utils.Constants.MAINTENANCE
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.ln

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val areas = LocalData.ciites
    var selectedAreaName = mutableStateOf("")
    var selectedCityName = mutableStateOf("")
    var selectedDistrictName = mutableStateOf("")
    var areaId = -1
    var cityId = -1
    var isFav = 0
    var lat = 0.0
    var lng = 0.0
    var districtId = -1
    var note = mutableStateOf("")

    fun getCitiesOfAreas(): List<City> {
        return areas?.find {
            it.id == areaId
        }?.cities ?: listOf()
    }

    fun getDistrictOfCities(): List<Children> {
        return areas?.find {
            it.id == areaId
        }?.cities?.find { it.id == cityId }?.children ?: listOf()
    }

    fun setSelectedArea(area: String, areaId: Int) {
        selectedAreaName.value = area
        this.areaId = areaId
    }

    fun setSelectedCity(city: String, cityId: Int) {
        selectedCityName.value = city
        this.cityId = cityId
    }

    fun setSelectedDistrict(city: String, cityId: Int) {
        selectedDistrictName.value = city
        this.districtId = cityId
    }

    fun clearSelected() {
        selectedAreaName.value = ""
        this.areaId = -1
        selectedCityName.value = ""
        this.cityId = -1
        selectedDistrictName.value = ""
        this.districtId = -1
    }

    fun createAddress(): Address? {
        var address: Address? = null
        if (areaId != -1 && cityId != -1 && districtId != -1) {
            address = Address(
                areaId,
                cityId,
                districtId,
                lat,
                lng,
                note.value,
                isFav
            )
        }
        return address
    }

}
