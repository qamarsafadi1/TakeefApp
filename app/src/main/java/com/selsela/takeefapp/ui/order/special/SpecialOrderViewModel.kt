package com.selsela.takeefapp.ui.order.special

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selsela.jobsapp.utils.validateRequired
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.auth.model.auth.User
import com.selsela.takeefapp.data.auth.repository.AuthRepository
import com.selsela.takeefapp.data.order.model.special.SpecificOrder
import com.selsela.takeefapp.data.order.repository.SpecialOrderRepository
import com.selsela.takeefapp.ui.auth.AuthUiState
import com.selsela.takeefapp.ui.auth.NotificationUiState
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.retrofit.model.ErrorsData
import com.selsela.takeefapp.utils.retrofit.model.Status
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
import java.io.File
import javax.inject.Inject


/**
 * UiState for the Special Order
 */
data class SpecialOrderUiState(
    val onSuccess: StateEvent = consumed,
    val orders: List<SpecificOrder>? = null,
    val isLoading: Boolean = false,
    val onFailure: StateEventWithContent<ErrorsData> = consumed(),
)

@HiltViewModel
class SpecialOrderViewModel @Inject constructor(
    private val application: Application,
    private val repository: SpecialOrderRepository
) : ViewModel() {

    var name: MutableState<String> = mutableStateOf(LocalData.user?.name ?: "")
    var title: MutableState<String> = mutableStateOf("")
    var description: MutableState<String> = mutableStateOf("")
    var attachments: MutableList<File>? = mutableListOf()
    var isTitleValid: MutableState<Boolean> = mutableStateOf(true)
    var isDescriptionValid: MutableState<Boolean> = mutableStateOf(true)
    var isNameValid: MutableState<Boolean> = mutableStateOf(true)
    var errorMessage: MutableState<String> = mutableStateOf("")
    var errorMessageName: MutableState<String> = mutableStateOf("")
    var errorMessageDescription: MutableState<String> = mutableStateOf("")
    var isLoaded = false


    /**
     * State Subscribers
     */
    private val _uiState = MutableStateFlow(SpecialOrderUiState())
    val uiState: StateFlow<SpecialOrderUiState> = _uiState.asStateFlow()
    private var state: SpecialOrderUiState
        get() = _uiState.value
        set(newState) {
            _uiState.update { newState }
        }

    /**
     * Form Validation
     */
    private fun isFormValid(): Boolean {
        val nameValidationMessage = name.value.validateRequired(
            application.applicationContext, application.getString(
                R.string.name
            )
        )
        val titleValidationMessage = title.value.validateRequired(
            application.applicationContext, application.getString(
                R.string.order_title
            )
        )
        val descriptionValidationMessage = description.value.validateRequired(
            application.applicationContext, application.getString(
                R.string.order_details
            )
        )
        if (nameValidationMessage == "" && titleValidationMessage == ""
            && descriptionValidationMessage == ""
        ) {
            isNameValid.value = true
            isDescriptionValid.value = true
            isTitleValid.value = true
        } else {
            if (nameValidationMessage != "" && titleValidationMessage != "" && descriptionValidationMessage != "") {
                isNameValid.value = false
                isDescriptionValid.value = false
                isTitleValid.value = false
                errorMessage.value = "${titleValidationMessage}"
                errorMessageName.value = "${nameValidationMessage}"
                errorMessageDescription.value = "${descriptionValidationMessage}"
            } else {
                if (nameValidationMessage != "") {
                    errorMessageName.value = nameValidationMessage
                    errorMessage.value = ""
                    errorMessageDescription.value = ""
                    isNameValid.value = false
                    isTitleValid.value = true
                    isDescriptionValid.value = true
                    if (titleValidationMessage != "") {
                        errorMessage.value = titleValidationMessage
                        isTitleValid.value = false
                    }
                } else if (titleValidationMessage != "") {
                    errorMessage.value = titleValidationMessage
                    errorMessageName.value = ""
                    errorMessageDescription.value = ""
                    isNameValid.value = true
                    isTitleValid.value = false
                    isDescriptionValid.value = true
                    if (descriptionValidationMessage != "") {
                        errorMessageDescription.value = descriptionValidationMessage
                        isDescriptionValid.value = false
                    }
                } else {
                    errorMessage.value = ""
                    errorMessageName.value = ""
                    errorMessageDescription.value = descriptionValidationMessage
                    isNameValid.value = true
                    isTitleValid.value = true
                    isDescriptionValid.value = false
                }
            }
        }
        return isDescriptionValid.value && isNameValid.value && isTitleValid.value
    }

    fun validateTitleBorderColor(): Color {
        return if (errorMessage.value.isNotEmpty() && isTitleValid.value.not())
            Red
        else BorderColor
    }

    fun validateNameBorderColor(): Color {
        return if (errorMessageName.value.isNotEmpty() && isNameValid.value.not())
            Red
        else BorderColor
    }

    fun validateDescriptionBorderColor(): Color {
        return if (errorMessageDescription.value.isNotEmpty() && isDescriptionValid.value.not())
            Red
        else BorderColor
    }

    /////////////////////////////////////////  API REQUESTS  ///////////////////////////////////////

    /**
     * API Requests
     */

    fun placeOrder() {
        if (isFormValid()) {
            viewModelScope.launch {
                state = state.copy(
                    isLoading = true
                )
                repository.placeSpecialOrder(
                    username = name.value,
                    title = title.value,
                    description = description.value,
                    photos = attachments
                )
                    .collect { result ->
                        val specialOrderUiState = when (result.status) {
                            Status.SUCCESS -> {
                                SpecialOrderUiState(
                                    onSuccess = triggered
                                )
                            }

                            Status.LOADING ->
                                SpecialOrderUiState(
                                    isLoading = true
                                )

                            Status.ERROR -> SpecialOrderUiState(
                                onFailure = triggered(
                                    ErrorsData(
                                        result.errors,
                                        result.message,
                                    )
                                ),
                            )
                        }
                        state = specialOrderUiState
                    }
            }
        }
    }

    fun getSpecialOrders() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.getSpecialOrders()
                .collect { result ->
                    val specialOrderUiState = when (result.status) {
                        Status.SUCCESS -> {
                            isLoaded = true
                            SpecialOrderUiState(
                                orders = result.data?.specificOrders
                            )
                        }

                        Status.LOADING ->
                            SpecialOrderUiState(
                                isLoading = true
                            )

                        Status.ERROR -> SpecialOrderUiState(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                )
                            ),
                        )
                    }
                    state = specialOrderUiState
                }
        }
    }

    /**
     * reset handlers
     */
    fun onSuccess() {
        state = state.copy(onSuccess = consumed)
    }

    fun onFailure() {
        state = state.copy(onFailure = consumed())
    }
}