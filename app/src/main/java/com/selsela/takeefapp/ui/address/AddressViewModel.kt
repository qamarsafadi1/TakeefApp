package com.selsela.takeefapp.ui.address

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.selsela.takeefapp.data.address.GooglePlacesRepository
import com.selsela.takeefapp.data.auth.model.address.District
import com.selsela.takeefapp.data.auth.model.address.FavouriteAddresse
import com.selsela.takeefapp.data.auth.repository.AuthRepository
import com.selsela.takeefapp.data.config.model.city.Area
import com.selsela.takeefapp.data.config.model.city.Children
import com.selsela.takeefapp.data.config.model.city.City
import com.selsela.takeefapp.ui.common.State
import com.selsela.takeefapp.ui.home.Address
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.retrofit.model.ErrorsData
import com.selsela.takeefapp.utils.retrofit.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Arrays
import javax.inject.Inject


/**
 * UiState for Addresses
 */

data class AddressUiState(
    val state: State = State.IDLE,
    val addresses: List<FavouriteAddresse>? = null,
    val error: ErrorsData? = null,
)

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val googlePlacesRepository: GooglePlacesRepository,
    @ApplicationContext private val context: Context
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
    var isLoaded = false
    var placesClient: PlacesClient
    var token: AutocompleteSessionToken
    private var mResult: StringBuilder? = null

    var searchAddress = mutableStateListOf<Place>()

    init {
        if (!Places.isInitialized()) {
            Places.initialize(context, "AIzaSyA8AitrTSHiTtbvtlDbmrwqZH1HRq0LxLQ");
        }

        placesClient = Places.createClient(context);
        token = AutocompleteSessionToken.newInstance();
    }

    /**
     * State Subscribers
     */
    private val _uiState = MutableStateFlow(AddressUiState())
    val uiState: StateFlow<AddressUiState> = _uiState.asStateFlow()

    private var state: AddressUiState
        get() = _uiState.value
        set(newState) {
            _uiState.update { newState }
        }

    /**
     * API Requests
     */
    fun getFavAddresses() {
        viewModelScope.launch {
            state = state.copy(
                state = State.LOADING
            )
            repository.getAddresses()
                .collect { result ->
                    val addressUiState = when (result.status) {
                        Status.SUCCESS -> {
                            isLoaded = true
                            AddressUiState(
                                state = State.SUCCESS,
                                addresses = result.data?.favouriteAddresses
                            )
                        }

                        Status.LOADING ->
                            AddressUiState(
                                state = State.LOADING
                            )

                        Status.ERROR -> AddressUiState(
                            state = State.ERROR,
                            error = ErrorsData(
                                result.errors,
                                result.message,
                            )
                        )
                    }
                    state = addressUiState
                }
        }
    }


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

    fun updateSelectAddress(address: FavouriteAddresse) {
        selectedCityName.value = address.city.name
        selectedAreaName.value = address.area.name
        selectedDistrictName.value = address.district.name
        lat = address.latitude
        lng = address.longitude
        areaId = address.area.id
        cityId = address.city.id
        districtId = address.district.id
    }

    fun updateSelectAddress(address: Place, isFav: Int) {
        address.address.log("heyClick")
        var areaName = ""
        var cityName = ""
        var districtName = ""
        val area = LocalData.ciites?.find {
            address.address.contains(it.name)
        }
        val city = area?.cities?.findLast {
            address.address.contains(it.name)
        }
        val district = city?.children?.findLast {
            address.address.contains(it.name)
        }

        areaName = area?.name ?: ""
        cityName = city?.name ?: ""
        districtName = district?.name ?: ""
        selectedCityName.value = cityName
        selectedAreaName.value = areaName
        selectedDistrictName.value = districtName
        selectedCityName.log("selectedCityName")
        selectedAreaName.log("selectedAreaName")
        lat = address.latLng.latitude
        lng = address.latLng.longitude
        this.isFav = isFav
        areaId = area?.id ?: -1
        cityId = city?.id ?: -1
        districtId = district?.id ?: -1
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


    fun getGoggleSearchAddresses(query: String) {
        val request: FindAutocompletePredictionsRequest =
            FindAutocompletePredictionsRequest.builder() // Call either setLocationBias() OR setLocationRestriction().
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(query)
                .build()
        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                response.autocompletePredictions.forEach {
                    getLatLng(it.placeId)
                }
                searchAddress.size.log("searchAddress")
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    val apiException = exception as ApiException
                    "Place not found: ".log()
                }
            }
    }

    private fun getLatLng(placeID: String) {
        val request: FetchPlaceRequest =
            FetchPlaceRequest.builder(
                placeID, Arrays.asList(
                    Place.Field.ID,
                    Place.Field.ADDRESS, Place.Field.LAT_LNG
                )
            ).build()
        placesClient.fetchPlace(request).addOnSuccessListener {
            it.log("searchAddress")
            searchAddress.add(0, it.place)
            searchAddress.size.log("searchAddress")
        }.addOnFailureListener {

        }
    }

    fun <T> searchCities(query: String, list: List<T>?): MutableState<List<T>?> {
        val cities = if (query == "")
            list
        else {
            list?.filter {
                when (it) {
                    is Area -> it.name.contains(query)
                    is City -> it.name.contains(query)
                    else -> (it as Children).name.contains(query)
                }
            }
        }
        return mutableStateOf(cities)
    }
}
