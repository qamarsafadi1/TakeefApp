package com.selsela.takeefapp.ui.order.special

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.selsela.jobsapp.utils.validateRequired
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.utils.LocalData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {
    var name: MutableState<String> = mutableStateOf(LocalData.user?.name ?: "")
    var title: MutableState<String> = mutableStateOf("")
    var description: MutableState<String> = mutableStateOf("")
    var isTitleValid: MutableState<Boolean> = mutableStateOf(true)
    var isDescriptionValid: MutableState<Boolean> = mutableStateOf(true)
    var isNameValid: MutableState<Boolean> = mutableStateOf(true)
    var errorMessage: MutableState<String> = mutableStateOf("")
    var errorMessageName: MutableState<String> = mutableStateOf("")
    var errorMessageDescription: MutableState<String> = mutableStateOf("")

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

    fun placeOrder(){
        isFormValid()
    }
}